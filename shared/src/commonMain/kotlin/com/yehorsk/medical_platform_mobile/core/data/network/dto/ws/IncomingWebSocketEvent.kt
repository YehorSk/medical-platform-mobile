package com.yehorsk.medical_platform_mobile.core.data.network.dto.ws

import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Message
import kotlinx.serialization.Serializable

sealed interface IncomingWebSocketEvent {
    data class NewMessage(val message: Message) : IncomingWebSocketEvent
    data class AccessRevoked(val conversationId: String) : IncomingWebSocketEvent
    data class Error(val code: String, val message: String) : IncomingWebSocketEvent
}

@Serializable
data class ErrorPayloadDto(
    val code: String,
    val message: String
)

@Serializable
data class AccessRevokedPayloadDto(
    val conversationId: String
)

object WsType {
    const val NEW_MESSAGE = "NEW_MESSAGE"
    const val ACCESS_REVOKED = "ACCESS_REVOKED"
    const val ERROR = "ERROR"
}

@Serializable
private data class SendMessagePayloadDto(
    val conversationId: String,
    val content: String
)
