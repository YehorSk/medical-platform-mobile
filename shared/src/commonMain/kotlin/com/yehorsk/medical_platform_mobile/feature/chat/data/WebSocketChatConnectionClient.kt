package com.yehorsk.medical_platform_mobile.feature.chat.data

import com.yehorsk.medical_platform_mobile.core.data.network.ConnectionState
import com.yehorsk.medical_platform_mobile.core.data.network.KtorWebSocketConnector
import com.yehorsk.medical_platform_mobile.core.data.network.dto.ws.ErrorPayloadDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.ws.IncomingWebSocketEvent
import com.yehorsk.medical_platform_mobile.core.data.network.dto.ws.WebSocketMessageDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.ws.WsType
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.EmptyResult
import com.yehorsk.medical_platform_mobile.feature.chat.data.dto.MessageDto
import com.yehorsk.medical_platform_mobile.feature.chat.data.mappers.toDomain
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Message
import com.yehorsk.medical_platform_mobile.feature.chat.domain.service.ChatConnectionClient
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
private data class SendMessagePayloadDto(
    val conversationId: String,
    val content: String
)

class WebSocketChatConnectionClient(
    private val webSocketConnector: KtorWebSocketConnector,
    private val json: Json,
    private val logger: MainLogger,
    applicationScope: CoroutineScope
) : ChatConnectionClient {

    private val events: Flow<IncomingWebSocketEvent> = webSocketConnector
        .messages
        .mapNotNull { parseIncoming(it) }
        .onEach { event ->
            if (event is IncomingWebSocketEvent.Error) {
                logger.debug("WS server error -> ${event.code}: ${event.message}")
            }
        }
        .shareIn(
            scope = applicationScope,
            started = SharingStarted.WhileSubscribed(5000)
        )

    override val chatMessages: Flow<Message> = events
        .filterIsInstance<IncomingWebSocketEvent.NewMessage>()
        .map { it.message }

    override val connectionState: StateFlow<ConnectionState> = webSocketConnector.connectionState

    private fun parseIncoming(envelope: WebSocketMessageDto): IncomingWebSocketEvent? {
        return try {
            when (envelope.type) {
                WsType.NEW_MESSAGE -> IncomingWebSocketEvent.NewMessage(
                    json.decodeFromString<MessageDto>(envelope.payload).toDomain()
                )
                WsType.ERROR -> {
                    val error = json.decodeFromString<ErrorPayloadDto>(envelope.payload)
                    IncomingWebSocketEvent.Error(error.code, error.message)
                }
                else -> null
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            logger.debug("Failed to parse WS message -> $e")
            null
        }
    }

    override suspend fun sendMessage(
        conversationId: String,
        content: String
    ): EmptyResult<DataError.Connection> {
        val payload = json.encodeToString(SendMessagePayloadDto(conversationId, content))
        val envelope = json.encodeToString(
            WebSocketMessageDto(type = WsType.NEW_MESSAGE, payload = payload)
        )
        return webSocketConnector.sendMessage(envelope)
    }
}