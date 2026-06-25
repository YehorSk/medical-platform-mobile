package com.yehorsk.medical_platform_mobile.feature.auth.domain.models

data class AuthData(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)