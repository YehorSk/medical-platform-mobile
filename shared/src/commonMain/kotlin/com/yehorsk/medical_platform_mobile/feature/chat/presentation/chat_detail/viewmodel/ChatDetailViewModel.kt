package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.data.network.ConnectionState
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.domain.model.AccessStatus
import com.yehorsk.medical_platform_mobile.core.domain.repository.SessionStorage
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.core.util.toUiText
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Conversation
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.ConversationBlockReason
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Message
import com.yehorsk.medical_platform_mobile.util.UiText
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.chat_notice_access_revoked
import medicalplatformmobile.shared.generated.resources.chat_notice_not_approved
import medicalplatformmobile.shared.generated.resources.chat_notice_unavailable
import kotlin.time.Instant
import com.yehorsk.medical_platform_mobile.feature.chat.domain.service.ChatConnectionClient
import com.yehorsk.medical_platform_mobile.feature.chat.domain.service.ConversationService
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.mappers.toUiList
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatDetailViewModel(
    private val conversationService: ConversationService,
    private val sessionStorage: SessionStorage,
    private val connectionClient: ChatConnectionClient,
    private val logger: MainLogger
) : ViewModel() {

    private val eventChannel = Channel<ChatDetailEvent>(Channel.BUFFERED)
    val events = eventChannel.receiveAsFlow()

    private val _chatId = MutableStateFlow<String?>(null)

    private val _state = MutableStateFlow(ChatDetailState())

    private val _conversation = MutableStateFlow<Conversation?>(null)
    private val _messages = MutableStateFlow<List<Message>>(emptyList())

    private val userId = sessionStorage.observeAuthData()
        .map { it?.user?.id }
        .distinctUntilChanged()

    private val pendingMessages = combine(connectionClient.pendingMessages, _chatId) { pending, chatId ->
        pending.filter { it.conversationId == chatId }
    }

    private val uiMessages = combine(_messages, userId, pendingMessages) { messages, id, pending ->
        if (id != null) messages.toUiList(id, pending) else emptyList()
    }

    // From REST (relation status, 404) when the chat opens
    private val _restBlock = MutableStateFlow<ConversationBlockReason?>(null)

    // A live signal from the socket (ACCESS_REVOKED, CONVERSATION_NOT_ACTIVE) wins over the REST snapshot
    private val blockReason = combine(
        _chatId,
        connectionClient.blockedConversations,
        _restBlock
    ) { chatId, blocked, restBlock ->
        chatId?.let { blocked[it] } ?: restBlock
    }

    private var conversationJob: Job? = null
    private var connectionJob: Job? = null
    private var messagesJob: Job? = null
    private var accessJob: Job? = null

    private val blockAndConnection = combine(blockReason, connectionClient.connectionState) { block, connection ->
        block to connection
    }

    val state = combine(
        _state,
        _conversation,
        uiMessages,
        userId,
        blockAndConnection
    ) { state, conversation, messages, userId, (blockReason, connection) ->
        val content = state.message.trim()
        val isTooLong = content.length > MAX_MESSAGE_LENGTH
        state.copy(
            conversation = conversation,
            messages = messages,
            localUserId = userId,
            blockReason = blockReason,
            inputNotice = blockReason?.toNotice(),
            isMessageTooLong = isTooLong,
            isConnecting = connection != ConnectionState.CONNECTED,
            // Sending while reconnecting is fine: the message is queued and goes out after reconnect
            canSendMessage = conversation != null &&
                    blockReason == null &&
                    content.isNotEmpty() &&
                    !isTooLong
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ChatDetailState()
    )

    init {
        observeIncomingMessages()
        connectionClient.connectionState
            .onEach { logger.debug("WS state -> $it") }
            .launchIn(viewModelScope)
        reloadWhenBackOnline()
    }

    // Opened offline: the conversation request failed and messages were never requested,
    // so load everything once the socket is connected again
    private fun reloadWhenBackOnline() {
        connectionClient.connectionState
            .map { it == ConnectionState.CONNECTED }
            .distinctUntilChanged()
            .filter { it }
            .onEach {
                val chatId = _chatId.value ?: return@onEach
                if (_conversation.value == null && conversationJob?.isActive != true) {
                    logger.debug("Back online, reloading conversation $chatId")
                    retry()
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: ChatDetailAction) {
        when (action) {
            is ChatDetailAction.OnSelectChat -> switchChat(action.chatId)
            is ChatDetailAction.OnMessageChange -> _state.update { it.copy(message = action.text) }
            ChatDetailAction.OnSendMessageClick -> sendMessage()
            ChatDetailAction.OnRetryClick -> retry()
            is ChatDetailAction.OnRetryMessageClick -> connectionClient.retryMessage(action.localId)
            is ChatDetailAction.OnDiscardMessageClick -> connectionClient.discardMessage(action.localId)
        }
    }

    private fun cancelLoading() {
        conversationJob?.cancel()
        connectionJob?.cancel()
        messagesJob?.cancel()
        accessJob?.cancel()
    }

    fun switchChat(chatId: String?) {
        if (_chatId.value == chatId) return

        _chatId.value = chatId
        _conversation.value = null
        _messages.value = emptyList()
        _restBlock.value = null
        _state.value = ChatDetailState(chatId = chatId, isLoading = chatId != null)

        if (chatId != null) startLoading(chatId)
        else cancelLoading()
    }

    private fun retry() {
        val chatId = _chatId.value ?: return
        _state.update { it.copy(isLoading = true, error = null) }
        startLoading(chatId)
    }

    private fun startLoading(chatId: String) {
        cancelLoading()
        loadConversation(chatId) {
            observeConnectionAndLoadMessages(chatId)
        }
    }

    private fun observeIncomingMessages() {
        connectionClient.chatMessages
            .filter { it.conversationId == _chatId.value }
            .onEach { message ->
                logger.debug("WS new message -> ${message.id}")
                _messages.update { it.mergeWith(listOf(message)) }
            }
            .launchIn(viewModelScope)
    }

    private fun loadConversation(chatId: String, onLoaded: () -> Unit) {
        logger.debug("REST loading conversation $chatId")

        conversationJob?.cancel()
        conversationJob = viewModelScope.launch {
            conversationService.getConversationById(chatId)
                .onSuccess { response ->
                    if (_chatId.value != chatId) return@onSuccess
                    _conversation.value = response.data
                    loadAccessStatus(response.data)
                    onLoaded()
                }
                .onFailure { error ->
                    if (_chatId.value != chatId) return@onFailure
                    logger.debug("REST conversation failed -> $error")
                    if (error == DataError.Remote.Status.NOT_FOUND) {
                        _restBlock.value = ConversationBlockReason.UNAVAILABLE
                    }
                    _state.update { it.copy(isLoading = false, error = error.toChatUiText()) }
                }
        }
    }

    /**
     * Sending needs an APPROVED patient–doctor relation. Checking it up front disables the input
     * before the user types; if the lookup fails, the server's CONVERSATION_NOT_ACTIVE still covers it.
     */
    private fun loadAccessStatus(conversation: Conversation) {
        accessJob?.cancel()
        accessJob = viewModelScope.launch {
            conversationService.getConversationAccess()
                .onSuccess { relations ->
                    if (_chatId.value != conversation.id) return@onSuccess
                    val relation = relations
                        .filter {
                            it.doctorId == conversation.doctor.userId &&
                                    it.patientId == conversation.patient.userId
                        }
                        .maxByOrNull { it.createdAt ?: Instant.DISTANT_PAST }
                    logger.debug("Conversation ${conversation.id} relation -> ${relation?.status}")

                    _restBlock.value = when (relation?.status) {
                        AccessStatus.APPROVED -> {
                            connectionClient.clearConversationBlock(conversation.id)
                            null
                        }
                        AccessStatus.REVOKED -> ConversationBlockReason.ACCESS_REVOKED
                        AccessStatus.PENDING,
                        AccessStatus.REJECTED -> ConversationBlockReason.NOT_APPROVED
                        // No match / unknown status: let the server decide on send
                        AccessStatus.UNKNOWN, null -> null
                    }
                }
                .onFailure { error ->
                    logger.debug("REST relation status failed -> $error")
                }
        }
    }

    private fun loadMessages(chatId: String) {
        logger.debug("REST loading messages for $chatId")

        messagesJob?.cancel()
        messagesJob = viewModelScope.launch {
            conversationService.getConversationMessages(chatId, limit = 50)
                .onSuccess { response ->
                    if (_chatId.value != chatId) return@onSuccess
                    _messages.update { it.mergeWith(response.data) }
                    _state.update { it.copy(isLoading = false, error = null, endReached = true) }
                }
                .onFailure { error ->
                    if (_chatId.value != chatId) return@onFailure
                    logger.debug("REST messages failed -> $error")
                    // 404 CONVERSATION_NOT_FOUND: the user isn't a participant (anymore)
                    if (error == DataError.Remote.Status.NOT_FOUND) {
                        _restBlock.value = ConversationBlockReason.UNAVAILABLE
                    }
                    _state.update {
                        // A failed reload shouldn't replace a chat that's already on screen.
                        if (_messages.value.isEmpty()) {
                            it.copy(isLoading = false, error = error.toChatUiText())
                        } else {
                            it.copy(isLoading = false)
                        }
                    }
                }
        }
    }

    private fun sendMessage() {
        val current = state.value
        val chatId = _chatId.value ?: return
        if (!current.canSendMessage) return

        // Queued in the client: shown as "sending" now, resent after a reconnect, marked failed on an ERROR
        connectionClient.sendMessage(chatId, current.message.trim())
        _state.update { it.copy(message = "") }
    }

    private fun DataError.Remote.toChatUiText(): UiText =
        if (this == DataError.Remote.Status.NOT_FOUND) UiText.Resource(UiRes.string.chat_notice_unavailable)
        else toUiText()

    private fun ConversationBlockReason.toNotice(): UiText = UiText.Resource(
        when (this) {
            ConversationBlockReason.ACCESS_REVOKED -> UiRes.string.chat_notice_access_revoked
            ConversationBlockReason.NOT_APPROVED -> UiRes.string.chat_notice_not_approved
            ConversationBlockReason.UNAVAILABLE -> UiRes.string.chat_notice_unavailable
        }
    )

    private fun List<Message>.mergeWith(incoming: List<Message>): List<Message> =
        (incoming + this)
            .distinctBy { it.id }
            .sortedByDescending { it.createdAt }

    private fun observeConnectionAndLoadMessages(chatId: String) {
        loadMessages(chatId)

        var wasDisconnected = false
        connectionJob?.cancel()
        connectionJob = connectionClient.connectionState
            .map { it == ConnectionState.CONNECTED }
            .distinctUntilChanged()
            .onEach { connected ->
                if (!connected) {
                    wasDisconnected = true
                } else if (wasDisconnected) {
                    wasDisconnected = false
                    loadMessages(chatId)
                }
            }
            .launchIn(viewModelScope)
    }

    private companion object {
        // Server rejects longer messages with INVALID_CONTENT
        const val MAX_MESSAGE_LENGTH = 2000
    }
}