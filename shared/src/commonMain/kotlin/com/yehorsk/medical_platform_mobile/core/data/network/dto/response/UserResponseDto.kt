package com.yehorsk.medical_platform_mobile.core.data.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDto(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: String,
    val title: String,
    val phone: String,
    val address: String,
    val emergencyContactPhone: String,
    val emergencyContactName: String,
)