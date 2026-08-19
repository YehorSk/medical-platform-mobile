package com.yehorsk.medical_platform_mobile.feature.appointments.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlin.time.Instant

data class Appointment(
    val id: String,
    val doctor: AppointmentDoctor? = null,
    val patient: AppointmentPatient? = null,
    val status: AppointmentStatus,
    val note: String,
    val date: LocalDate,
    val time: LocalTime,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class AppointmentDoctor(
    val id: String,
    val firstName: String,
    val lastName: String,
    val title: String,
    val specialization: String
) {
    val fullName: String
        get() = listOf(title, firstName, lastName)
            .filter { it.isNotBlank() }
            .joinToString(" ")
}

data class AppointmentPatient(
    val id: String,
    val firstName: String,
    val lastName: String,
    val title: String
) {
    val fullName: String
        get() = listOf(title, firstName, lastName)
            .filter { it.isNotBlank() }
            .joinToString(" ")
}
enum class AppointmentStatus {
    PENDING,
    CONFIRMED,
    REJECTED,
    CANCELLED,
    COMPLETED,
    UNKNOWN
}