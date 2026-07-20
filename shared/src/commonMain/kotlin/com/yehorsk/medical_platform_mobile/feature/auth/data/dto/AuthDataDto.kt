package com.yehorsk.medical_platform_mobile.feature.auth.data.dto

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.UserResponseDto
import kotlinx.serialization.Serializable

@Serializable
data class AuthDataDto(
    val user: UserResponseDto,
    val accessToken: String,
    val refreshToken: String
)