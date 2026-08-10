package com.yehorsk.medical_platform_mobile.feature.appointments.domain.model

import com.yehorsk.medical_platform_mobile.core.domain.model.AppointmentStatus
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlin.time.Instant

data class AppointmentResponseDto(
    val id: String,
    val doctorId: String,
    val doctorName: String,
    val patientId: String,
    val patientName: String,
    val status: AppointmentStatus,
    val note: String,
    val date: LocalDate,
    val time: LocalTime,
    val createdAt: Instant,
    val updatedAt: Instant
)
