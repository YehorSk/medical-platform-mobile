package com.yehorsk.medical_platform_mobile.feature.auth.domain.model

data class AuthResponse(
    val token: String,
    val user: User,
    val message: String? = null
)