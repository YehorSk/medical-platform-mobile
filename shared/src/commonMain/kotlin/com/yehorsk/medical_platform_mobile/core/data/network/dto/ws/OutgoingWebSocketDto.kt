package com.yehorsk.medical_platform_mobile.core.data.network.dto.ws

import kotlinx.serialization.Serializable

enum class OutgoingWebSocketType {
    NEW_MESSAGE
}

@Serializable
sealed class OutgoingWebSocketDto(
    val type: OutgoingWebSocketType
) {

    @Serializable
    data class NewMessage(
        val chatId: String,
        val messageId: String,
        val content: String
    ): OutgoingWebSocketDto(OutgoingWebSocketType.NEW_MESSAGE)
}