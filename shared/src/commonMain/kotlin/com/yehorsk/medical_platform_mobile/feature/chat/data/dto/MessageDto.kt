package com.yehorsk.medical_platform_mobile.feature.chat.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val id: String,
    val conversationId: String,
    val senderId: String,
    val content: String,
    val createdAt: String
)