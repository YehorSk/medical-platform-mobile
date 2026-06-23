package com.yehorsk.medical_platform_mobile.feature.auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthDataDto(
    val user: UserDto,
    val message: String? = null,
    val accessToken: String,
    val refreshToken: String
)