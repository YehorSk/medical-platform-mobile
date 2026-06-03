package com.yehorsk.medical_platform_mobile.core.domain.model

data class Conversation(
    val id: Int,
    val createdAt: String,
    val patient: Patient,
    val doctor: Doctor,
    val lastMessage: Message? = null
)
