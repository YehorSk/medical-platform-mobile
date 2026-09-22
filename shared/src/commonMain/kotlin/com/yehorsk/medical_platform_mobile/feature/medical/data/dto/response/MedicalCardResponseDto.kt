package com.yehorsk.medical_platform_mobile.feature.medical.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class MedicalCardResponseDto(
    val id: String,
    val bloodType: String = "",
    val gender: String = "",
    val insuranceCompany: String = "",
    val insuranceNumber: String = "",
    val dateOfBirth: String = "",
    val patient: MedicalCardPatientDto? = null,
    val createdAt: String,
    val updatedAt: String
)

