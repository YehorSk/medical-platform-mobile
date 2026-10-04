package com.yehorsk.medical_platform_mobile.feature.chat.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ConversationDto(
    val id: String,
    val patient: ParticipantDto,
    val doctor: ParticipantDto,
    val lastMessageAt: String? = null,
    val lastMessage: MessageDto? = null
)