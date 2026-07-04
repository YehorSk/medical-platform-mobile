package com.yehorsk.medical_platform_mobile.feature.auth.domain.models

import com.yehorsk.medical_platform_mobile.core.domain.model.User

data class AuthData(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)