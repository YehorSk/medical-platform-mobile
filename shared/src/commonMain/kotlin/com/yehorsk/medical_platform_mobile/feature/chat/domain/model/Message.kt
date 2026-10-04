package com.yehorsk.medical_platform_mobile.feature.chat.domain.model

import kotlin.time.Instant

data class Message(
    val id: String,
    val conversationId: String,
    val senderId: String,
    val content: String,
    val createdAt: Instant
)