package com.yehorsk.medical_platform_mobile.feature.auth.domain.models

data class User(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: String
)