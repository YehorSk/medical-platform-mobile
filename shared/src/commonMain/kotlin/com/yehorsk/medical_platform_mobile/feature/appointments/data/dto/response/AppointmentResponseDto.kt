package com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.response

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.UserResponseDto
import kotlinx.serialization.Serializable

@Serializable
data class AppointmentResponseDto(
    val id: String,
    val doctor: UserResponseDto,
    val patient: UserResponseDto? = null,
    val specialization: String = "",
    val status: String,
    val note: String,
    val date: String,
    val time: String,
    val createdAt: String,
    val updatedAt: String
)
