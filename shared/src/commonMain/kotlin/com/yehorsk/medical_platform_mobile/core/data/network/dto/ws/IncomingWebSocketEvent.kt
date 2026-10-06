package com.yehorsk.medical_platform_mobile.core.data.network.dto.ws

import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Message
import kotlinx.serialization.Serializable

sealed interface IncomingWebSocketEvent {
    data class NewMessage(val message: Message) : IncomingWebSocketEvent
    data class Error(val code: String, val message: String) : IncomingWebSocketEvent
}

@Serializable
data class ErrorPayloadDto(
    val code: String,
    val message: String
)

object WsType {
    const val NEW_MESSAGE = "NEW_MESSAGE"
    const val ERROR = "ERROR"
}

@Serializable
private data class SendMessagePayloadDto(
    val conversationId: String,
    val content: String
)