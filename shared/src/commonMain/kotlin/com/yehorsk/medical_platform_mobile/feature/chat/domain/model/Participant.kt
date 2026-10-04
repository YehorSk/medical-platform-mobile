package com.yehorsk.medical_platform_mobile.feature.chat.domain.model

data class Participant(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val title: String? = null
)
