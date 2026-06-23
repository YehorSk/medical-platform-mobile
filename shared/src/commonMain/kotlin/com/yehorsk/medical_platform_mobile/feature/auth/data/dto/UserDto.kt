package com.yehorsk.medical_platform_mobile.feature.auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: String
)