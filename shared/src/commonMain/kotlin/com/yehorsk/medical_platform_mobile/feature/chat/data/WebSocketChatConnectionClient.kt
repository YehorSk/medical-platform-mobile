package com.yehorsk.medical_platform_mobile.feature.chat.data

import com.yehorsk.medical_platform_mobile.core.data.network.ConnectionState
import com.yehorsk.medical_platform_mobile.core.data.network.KtorWebSocketConnector
import com.yehorsk.medical_platform_mobile.core.data.network.dto.ws.AccessRevokedPayloadDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.ws.ErrorPayloadDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.ws.IncomingWebSocketEvent
import com.yehorsk.medical_platform_mobile.core.data.network.dto.ws.WebSocketMessageDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.ws.WsType
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.domain.repository.SessionStorage
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.chat.data.dto.MessageDto
import com.yehorsk.medical_platform_mobile.feature.chat.data.mappers.toDomain
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.ChatSendError
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.ConversationBlockReason
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Message
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.PendingMessage
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.PendingMessageStatus
import com.yehorsk.medical_platform_mobile.feature.chat.domain.service.ChatConnectionClient
import com.yehorsk.medical_platform_mobile.feature.chat.domain.service.ConversationService
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
private data class SendMessagePayloadDto(
    val conversationId: String,
    val content: String
)

class WebSocketChatConnectionClient(
    private val webSocketConnector: KtorWebSocketConnector,
    private val conversationService: ConversationService,
    private val sessionStorage: SessionStorage,
    private val json: Json,
    private val logger: MainLogger,
    private val applicationScope: CoroutineScope
) : ChatConnectionClient {

    private val _pendingMessages = MutableStateFlow<List<PendingMessage>>(emptyList())
    override val pendingMessages: StateFlow<List<PendingMessage>> = _pendingMessages.asStateFlow()

    private val _blockedConversations = MutableStateFlow<Map<String, ConversationBlockReason>>(emptyMap())
    override val blockedConversations: StateFlow<Map<String, ConversationBlockReason>> =
        _blockedConversations.asStateFlow()

    // Written to the current socket and waiting for the echo or an ERROR, in send order.
    // The server handles one session's frames in order, so an ERROR belongs to the oldest one.
    private val inFlight = ArrayDeque<String>()

    // Server messages already matched to a pending one, so the same message can't confirm two
    private val confirmedMessageIds = mutableSetOf<String>()

    private val outboxMutex = Mutex()

    private val events: Flow<IncomingWebSocketEvent> = webSocketConnector
        .messages
        .mapNotNull { parseIncoming(it) }
        .onEach { event -> handleEvent(event) }
        .shareIn(
            scope = applicationScope,
            started = SharingStarted.WhileSubscribed(5000)
        )

    override val chatMessages: Flow<Message> = events
        .filterIsInstance<IncomingWebSocketEvent.NewMessage>()
        .map { it.message }

    override val connectionState: StateFlow<ConnectionState> = webSocketConnector.connectionState

    init {
        sessionStorage.observeAuthData()
            .map { it?.user?.id }
            .distinctUntilChanged()
            .filter { it == null }
            .onEach { clearOnLogout() }
            .launchIn(applicationScope)

        connectionState
            .map { it == ConnectionState.CONNECTED }
            .distinctUntilChanged()
            .filter { it }
            .onEach { onConnected() }
            .launchIn(applicationScope)
    }

    private fun parseIncoming(envelope: WebSocketMessageDto): IncomingWebSocketEvent? {
        return try {
            when (envelope.type) {
                WsType.NEW_MESSAGE -> IncomingWebSocketEvent.NewMessage(
                    json.decodeFromString<MessageDto>(envelope.payload).toDomain()
                )
                WsType.ACCESS_REVOKED -> IncomingWebSocketEvent.AccessRevoked(
                    json.decodeFromString<AccessRevokedPayloadDto>(envelope.payload).conversationId
                )
                WsType.ERROR -> {
                    val error = json.decodeFromString<ErrorPayloadDto>(envelope.payload)
                    IncomingWebSocketEvent.Error(error.code, error.message)
                }
                else -> {
                    logger.debug("Ignoring unknown WS message type -> ${envelope.type}")
                    null
                }
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            logger.debug("Failed to parse WS message -> $e")
            null
        }
    }

    private suspend fun handleEvent(event: IncomingWebSocketEvent) {
        when (event) {
            is IncomingWebSocketEvent.NewMessage -> confirmEcho(event.message)
            is IncomingWebSocketEvent.AccessRevoked -> {
                logger.debug("WS access revoked -> ${event.conversationId}")
                outboxMutex.withLock {
                    block(event.conversationId, ConversationBlockReason.ACCESS_REVOKED)
                }
            }
            is IncomingWebSocketEvent.Error -> {
                logger.debug("WS server error -> ${event.code}: ${event.message}")
                outboxMutex.withLock { failOldestInFlight(ChatSendError.fromCode(event.code)) }
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun sendMessage(conversationId: String, content: String) {
        val pending = PendingMessage(
            localId = Uuid.random().toString(),
            conversationId = conversationId,
            content = content,
            createdAt = Clock.System.now(),
            status = PendingMessageStatus.SENDING
        )
        _pendingMessages.update { it + pending }
        applicationScope.launch { flush() }
    }

    override fun retryMessage(localId: String) {
        _pendingMessages.update { messages ->
            messages.map {
                if (it.localId == localId && it.status == PendingMessageStatus.FAILED) {
                    it.copy(status = PendingMessageStatus.SENDING, error = null)
                } else it
            }
        }
        applicationScope.launch { flush() }
    }

    override fun discardMessage(localId: String) {
        // Only failed messages: anything in flight still has to line up with the server's replies
        _pendingMessages.update { messages ->
            messages.filterNot { it.localId == localId && it.status == PendingMessageStatus.FAILED }
        }
    }

    override fun clearConversationBlock(conversationId: String) {
        _blockedConversations.update { it - conversationId }
    }

    /** Sends every queued message that isn't on the wire yet, oldest first. */
    private suspend fun flush() = outboxMutex.withLock {
        for (pending in _pendingMessages.value) {
            if (pending.status != PendingMessageStatus.SENDING || pending.localId in inFlight) continue

            val blockReason = _blockedConversations.value[pending.conversationId]
            if (blockReason != null) {
                markFailed(pending.localId, blockReason.toSendError())
                continue
            }

            if (connectionState.value != ConnectionState.CONNECTED) break

            val payload = json.encodeToString(SendMessagePayloadDto(pending.conversationId, pending.content))
            val envelope = json.encodeToString(
                WebSocketMessageDto(type = WsType.NEW_MESSAGE, payload = payload)
            )
            when (webSocketConnector.sendMessage(envelope)) {
                is Result.Success -> inFlight.addLast(pending.localId)
                // Socket went away; the rest goes out after the reconnect
                is Result.Failure -> break
            }
        }
    }

    /**
     * Whatever was in flight when the old socket closed got no answer. When the server dropped the
     * socket for an expired token the frame wasn't processed, but after a network drop it may have
     * been, so check the latest messages before resending to avoid duplicates.
     */
    private suspend fun onConnected() {
        outboxMutex.withLock {
            val unanswered = inFlight.toList()
            inFlight.clear()
            if (unanswered.isNotEmpty()) reconcile(unanswered)
        }
        flush()
    }

    private suspend fun reconcile(localIds: List<String>) {
        val userId = currentUserId() ?: return
        _pendingMessages.value
            .filter { it.localId in localIds }
            .groupBy { it.conversationId }
            .forEach { (conversationId, messages) ->
                conversationService
                    .getConversationMessages(conversationId, limit = RECONCILE_PAGE_SIZE)
                    .onSuccess { response ->
                        messages.forEach { pending ->
                            val delivered = response.data.firstOrNull {
                                it.sender.userId == userId &&
                                        it.content == pending.content &&
                                        it.id !in confirmedMessageIds &&
                                        it.createdAt >= pending.createdAt - CLOCK_SKEW_TOLERANCE
                            }
                            if (delivered != null) {
                                confirmedMessageIds += delivered.id
                                _pendingMessages.update { list -> list.filterNot { it.localId == pending.localId } }
                            }
                        }
                    }
            }
    }

    private suspend fun confirmEcho(message: Message) {
        if (message.sender.userId != currentUserId()) return
        outboxMutex.withLock {
            val candidates = _pendingMessages.value.filter {
                it.conversationId == message.conversationId &&
                        it.content == message.content &&
                        it.status == PendingMessageStatus.SENDING
            }
            val match = candidates.firstOrNull { it.localId in inFlight } ?: candidates.firstOrNull()
                ?: return@withLock
            inFlight.remove(match.localId)
            confirmedMessageIds += message.id
            _pendingMessages.update { list -> list.filterNot { it.localId == match.localId } }
        }
    }

    private fun failOldestInFlight(error: ChatSendError) {
        val localId = inFlight.removeFirstOrNull() ?: return
        val pending = _pendingMessages.value.firstOrNull { it.localId == localId } ?: return
        markFailed(localId, error)
        when (error) {
            ChatSendError.CONVERSATION_NOT_ACTIVE ->
                block(pending.conversationId, ConversationBlockReason.NOT_APPROVED)
            ChatSendError.FORBIDDEN ->
                block(pending.conversationId, ConversationBlockReason.UNAVAILABLE)
            else -> Unit
        }
    }

    private fun block(conversationId: String, reason: ConversationBlockReason) {
        _blockedConversations.update { blocked ->
            // A revoke is more specific than "not active", keep it
            if (blocked[conversationId] == ConversationBlockReason.ACCESS_REVOKED) blocked
            else blocked + (conversationId to reason)
        }
        // Queued messages for it would only bounce; in-flight ones get their own ERROR
        _pendingMessages.value
            .filter {
                it.conversationId == conversationId &&
                        it.status == PendingMessageStatus.SENDING &&
                        it.localId !in inFlight
            }
            .forEach { markFailed(it.localId, reason.toSendError()) }
    }

    private fun markFailed(localId: String, error: ChatSendError) {
        _pendingMessages.update { messages ->
            messages.map {
                if (it.localId == localId) it.copy(status = PendingMessageStatus.FAILED, error = error) else it
            }
        }
    }

    private suspend fun clearOnLogout() = outboxMutex.withLock {
        inFlight.clear()
        confirmedMessageIds.clear()
        _pendingMessages.value = emptyList()
        _blockedConversations.value = emptyMap()
    }

    private suspend fun currentUserId(): String? =
        sessionStorage.observeAuthData().firstOrNull()?.user?.id

    private fun ConversationBlockReason.toSendError(): ChatSendError = when (this) {
        ConversationBlockReason.ACCESS_REVOKED,
        ConversationBlockReason.NOT_APPROVED -> ChatSendError.CONVERSATION_NOT_ACTIVE
        ConversationBlockReason.UNAVAILABLE -> ChatSendError.FORBIDDEN
    }

    private companion object {
        const val RECONCILE_PAGE_SIZE = 20
        val CLOCK_SKEW_TOLERANCE = 2.minutes
    }
}
