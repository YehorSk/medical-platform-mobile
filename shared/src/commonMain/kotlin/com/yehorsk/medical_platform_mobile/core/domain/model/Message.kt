package com.yehorsk.medical_platform_mobile.core.domain.model

import kotlin.time.Instant

data class Message(
    val id: Int,
    val conversationId: Int,
    val sender: User,
    val content: String,
    val isRead: Boolean,
    val createdAt: Instant
)