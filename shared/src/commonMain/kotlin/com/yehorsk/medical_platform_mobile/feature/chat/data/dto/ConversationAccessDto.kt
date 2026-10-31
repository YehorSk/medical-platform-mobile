package com.yehorsk.medical_platform_mobile.feature.chat.data.dto

import kotlinx.serialization.Serializable

// Only the fields chat needs from GET /patient-doctor-access/all (PatientHasDoctorResponse)
@Serializable
data class ConversationAccessDto(
    val id: String,
    val doctor: AccessUserDto,
    val medicalCard: AccessMedicalCardDto,
    val status: String,
    val createdAt: String
)

@Serializable
data class AccessUserDto(
    val id: String
)

@Serializable
data class AccessMedicalCardDto(
    val patient: AccessUserDto? = null
)
