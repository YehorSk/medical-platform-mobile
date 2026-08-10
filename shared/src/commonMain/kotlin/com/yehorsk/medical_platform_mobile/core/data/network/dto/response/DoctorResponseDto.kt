package com.yehorsk.medical_platform_mobile.core.data.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class DoctorResponseDto(
    val id: String,
    val licenseNumber: String,
    val user: UserResponseDto? = null,
    val approvedBy: UserResponseDto? = null,
    val approved: Boolean = false,
    val description: String = "",
    val specialization: SpecializationResponseDto? = null,
    val workplace: WorkplaceResponseDto? = null,
    val schedules: List<DayScheduleResponseDto> = emptyList(),
    val createdAt: String,
    val updatedAt: String,
    val approvedAt: String,
    val currentPatientHasDoctor: Boolean
)
