package com.yehorsk.medical_platform_mobile.feature.chat.domain.model

import kotlin.time.Instant

data class Conversation(
    val id: String,
    val patient: Participant,
    val doctor: Participant,
    val lastMessageAt: Instant? = null,
    val lastMessage: Message
)
