package com.yehorsk.medical_platform_mobile.core.domain.model

import kotlin.time.Instant

data class Conversation(
    val id: Int,
    val createdAt: Instant,
    val patient: User,
    val doctor: User,
    val lastMessage: Message
)
