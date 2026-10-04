package com.yehorsk.medical_platform_mobile.feature.chat.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ParticipantDto(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val title: String? = null
)