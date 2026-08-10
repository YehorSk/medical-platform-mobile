package com.yehorsk.medical_platform_mobile.core.domain.model

import com.yehorsk.medical_platform_mobile.util.getRole
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class User(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: String,
    val title: String = "",
    val phone: String = "",
    val address: String = "",
    val emergencyContact: String = "",
    val emergencyContactName: String = "",
    val emergencyContactPhone: String = "",
    val createdAt: Instant ? = null,
    val patient: Patient? = null,
    val doctor: Doctor? = null
){
    fun getUserRole(): UserRole{
        return getRole(role)
    }
}

enum class UserRole {
    PATIENT, DOCTOR, ADMIN
}