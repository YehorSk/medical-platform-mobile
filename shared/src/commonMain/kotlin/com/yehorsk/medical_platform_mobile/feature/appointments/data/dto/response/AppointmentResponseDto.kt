package com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class AppointmentResponseDto(
    val id: String,
    val doctorId: String,
    val doctorName: String,
    val patientId: String,
    val patientName: String,
    val status: String,
    val note: String,
    val date: String,
    val time: String,
    val createdAt: String,
    val updatedAt: String
)
