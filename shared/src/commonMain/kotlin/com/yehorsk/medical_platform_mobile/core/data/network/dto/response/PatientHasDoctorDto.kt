package com.yehorsk.medical_platform_mobile.core.data.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class PatientHasDoctorDto(
    val id: String,
    val status: String,
    val initiatedBy: String,
    val createdAt: String,
    val updatedAt: String,
)
