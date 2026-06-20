package com.yehorsk.medical_platform_mobile.core.domain.model

import kotlin.time.Instant

data class Appointment(
    val id: Int,
    val datetime: Instant,
    val status: AppointmentStatus,
    val note: String? = null,
    val createdAt: Instant,
    val updatedAt: Instant,
    val patient: User? = null,
    val specialization: Specialization,
    val doctor: User
)

enum class AppointmentStatus {
    PENDING,
    CONFIRMED,
    REJECTED,
    CANCELLED,
    COMPLETED
}