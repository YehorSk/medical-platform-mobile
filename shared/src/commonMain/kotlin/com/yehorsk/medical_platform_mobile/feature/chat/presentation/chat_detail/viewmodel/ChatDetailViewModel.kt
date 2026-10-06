package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.data.network.ConnectionState
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.domain.repository.SessionStorage
import com.yehorsk.medical_platform_mobile.core.util.Paginator
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.core.util.toUiText
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.viewmodel.AppointmentDetailsState
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Conversation
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Message
import com.yehorsk.medical_platform_mobile.feature.chat.domain.service.ChatConnectionClient
import com.yehorsk.medical_platform_mobile.feature.chat.domain.service.ConversationService
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.mappers.toUi
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.mappers.toUiList
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.model.MessageUi
import com.yehorsk.medical_platform_mobile.util.DateUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

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

    private var chatJob: Job? = null

    val state = combine(
        _state,
        connectionClient.connectionState
    ) { state, connection ->
        state.copy(
            canSendMessage = state.conversation != null &&
                    connection == ConnectionState.CONNECTED &&
                    state.message.isNotBlank()
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
    }

    fun onAction(action: ChatDetailAction) {
        when (action) {
            is ChatDetailAction.OnSelectChat -> switchChat(action.chatId)
            is ChatDetailAction.OnMessageChange -> _state.update { it.copy(message = action.text) }
            ChatDetailAction.OnSendMessageClick -> sendMessage()
            ChatDetailAction.OnRetryClick -> retry()
            is ChatDetailAction.OnFirstVisibleIndexChanged ->
                _state.update { it.copy(isNearBottom = action.index <= 3) }
        }
    }

    fun switchChat(chatId: String?) {
        if (_chatId.value == chatId) return

        chatJob?.cancel()
        _state.value = ChatDetailState(isLoading = chatId != null)
        _chatId.value = chatId

        if (chatId != null) startLoading(chatId)
    }

    private fun retry() {
        val chatId = _chatId.value ?: return
        _state.update { it.copy(isLoading = true, error = null) }
        startLoading(chatId)
    }

    /**
     * Сначала данные о чате, затем при каждом CONNECTED
     * (первое подключение, возврат из фона, восстановление сети) грузим сообщения.
     * Отменяется при смене чата через chatJob.
     */
    private fun startLoading(chatId: String) {
        chatJob?.cancel()
        chatJob = viewModelScope.launch {
            if (!loadConversation(chatId)) return@launch

            connectionClient.connectionState
                .filter { it == ConnectionState.CONNECTED }
                .collect { loadMessages(chatId) }
        }
    }

    private fun observeIncomingMessages() {
        connectionClient.chatMessages
            .filter { it.conversationId == _chatId.value }
            .onEach { message ->
                logger.debug("WS new message -> ${message.id}: ${message.content}")
                addNewMessage(message)
                if (_state.value.isNearBottom) {
                    eventChannel.trySend(ChatDetailEvent.OnNewMessage)
                }
            }
            .launchIn(viewModelScope)
    }

    private suspend fun loadConversation(chatId: String): Boolean {
        logger.debug("REST loading conversation $chatId")
        var success = false

        conversationService.getConversationById(chatId)
            .onSuccess { response ->
                if (_chatId.value != chatId) return@onSuccess
                logger.debug("REST conversation loaded -> ${response.data}")
                _state.update { it.copy(conversation = response.data) }
                success = true
            }
            .onFailure { error ->
                if (_chatId.value != chatId) return@onFailure
                logger.debug("REST conversation failed -> $error")
                _state.update { it.copy(isLoading = false, error = error.toUiText()) }
            }

        return success
    }

    private suspend fun loadMessages(chatId: String) {
        logger.debug("REST loading messages for $chatId")

        conversationService.getConversationMessages(chatId, limit = 50)
            .onSuccess { response ->
                if (_chatId.value != chatId) return@onSuccess
                val userId = currentUserId() ?: return@onSuccess
                logger.debug("REST loaded ${response.data.size} messages")
                _state.update {
                    it.copy(
                        messages = response.data.toUiList(userId), // заменяем список целиком
                        isLoading = false,
                        error = null,
                        endReached = true
                    )
                }
            }
            .onFailure { error ->
                if (_chatId.value != chatId) return@onFailure
                logger.debug("REST messages failed -> $error")
                _state.update { it.copy(isLoading = false, error = error.toUiText()) }
            }
    }

    private suspend fun addNewMessage(message: Message) {
        val userId = currentUserId() ?: return
        val date = message.createdAt
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date

        _state.update { current ->
            // дубль (например, REST уже вернул это сообщение)
            if (current.messages.any { it.id == message.id }) return@update current

            val hasSeparator = current.messages.any {
                it is MessageUi.DateSeparator && it.id == date.toString()
            }

            val newItems = if (hasSeparator) {
                listOf(message.toUi(userId))
            } else {
                listOf(
                    message.toUi(userId),
                    MessageUi.DateSeparator(
                        id = date.toString(),
                        date = DateUtils.formatDateSeparator(date)
                    )
                )
            }

            current.copy(messages = newItems + current.messages)
        }
    }

    private fun sendMessage() {
        val chatId = _chatId.value ?: return
        val content = _state.value.message.trim()
        if (content.isBlank()) return

        viewModelScope.launch {
            connectionClient.sendMessage(chatId, content)
                .onSuccess {
                    logger.debug("WS message sent")
                    _state.update { it.copy(message = "") }
                }
                .onFailure { error ->
                    logger.debug("WS send failed -> $error")
                    eventChannel.send(ChatDetailEvent.OnError(error.toUiText()))
                }
        }
    }

    private suspend fun currentUserId(): String? =
        sessionStorage.observeAuthData().firstOrNull()?.user?.id
}