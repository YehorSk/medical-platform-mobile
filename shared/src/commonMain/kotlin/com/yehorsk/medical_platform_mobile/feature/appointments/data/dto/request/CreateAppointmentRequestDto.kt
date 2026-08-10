package com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateAppointmentRequestDto(
    val doctorId: String,
    val date: String,
    val time: String,
    val note: String = ""
)
