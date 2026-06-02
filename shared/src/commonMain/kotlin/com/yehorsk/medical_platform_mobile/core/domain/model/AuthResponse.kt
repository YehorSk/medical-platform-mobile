package com.yehorsk.medical_platform_mobile.core.domain.model

data class AuthResponse(
    val token: String,
    val user: User,
    val message: String? = null
)