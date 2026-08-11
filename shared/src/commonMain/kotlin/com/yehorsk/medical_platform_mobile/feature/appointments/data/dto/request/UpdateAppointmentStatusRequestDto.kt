package com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.request

import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentStatus
import kotlinx.serialization.Serializable

@Serializable
data class UpdateAppointmentStatusRequestDto(
    val appointmentId: String,
    val status: AppointmentStatus,
    val note: String = ""
)
