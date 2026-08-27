package com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class MedicalCardPatientDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val title: String
)