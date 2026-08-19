package com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.response

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.UserResponseDto
import kotlinx.serialization.Serializable

@Serializable
data class AppointmentResponseDto(
    val id: String,
    val doctor: AppointmentDoctorDto? = null,
    val patient: AppointmentPatientDto? = null,
    val status: String,
    val note: String,
    val date: String,
    val time: String,
    val createdAt: String,
    val updatedAt: String
)

@Serializable
data class AppointmentDoctorDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val title: String,
    val specialization: String
)

@Serializable
data class AppointmentPatientDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val title: String
)