package com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class PatientHasDoctorWithoutDoctorResponse(
    val id: String,
    val medicalCard: MedicalCardResponseDto,
    val status: String,
    val createdAt: String
)
