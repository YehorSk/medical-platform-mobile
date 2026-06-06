package com.yehorsk.medical_platform_mobile.core.domain.model

data class Conversation(
    val id: Int,
    val createdAt: String,
    val patient: User,
    val doctor: User,
    val lastMessage: Message
)
