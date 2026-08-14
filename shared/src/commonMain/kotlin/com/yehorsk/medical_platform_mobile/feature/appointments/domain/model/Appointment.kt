package com.yehorsk.medical_platform_mobile.feature.appointments.domain.model

import com.yehorsk.medical_platform_mobile.core.domain.model.User
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlin.time.Instant

data class Appointment(
    val id: String,
    val doctor: User? = null,
    val patient: User? = null,
    val specialization: String = "",
    val status: AppointmentStatus,
    val note: String,
    val date: LocalDate,
    val time: LocalTime,
    val createdAt: Instant,
    val updatedAt: Instant
)

enum class AppointmentStatus {
    PENDING,
    CONFIRMED,
    REJECTED,
    CANCELLED,
    COMPLETED,
    UNKNOWN
}