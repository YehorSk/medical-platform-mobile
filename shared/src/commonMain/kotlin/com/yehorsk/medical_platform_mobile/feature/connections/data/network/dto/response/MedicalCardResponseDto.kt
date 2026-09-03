package com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class MedicalCardResponseDto(
    val id: String,
    val bloodType: String? = null,
    val gender: String? = null,
    val insuranceCompany: String? = null,
    val insuranceNumber: String? = null,
    val dateOfBirth: String? = null,
    val patient: MedicalCardPatientDto? = null,
    val createdAt: String,
    val updatedAt: String
)

