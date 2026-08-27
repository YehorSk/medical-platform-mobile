package com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class MedicalCardResponseDto(
    val id: String,
    val bloodType: String = "",
    val insuranceNumber: String? = null,
    val user: MedicalCardPatientDto? = null,
    val createdAt: String,
    val updatedAt: String
)

