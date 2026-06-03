package com.yehorsk.medical_platform_mobile.core.domain.model

data class User(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: String,
    val title: String,
    val createdAt: String? = null,
    val patient: Patient? = null,
    val doctor: Doctor? = null
)

enum class UserRole {
    PATIENT, DOCTOR, ADMIN
}