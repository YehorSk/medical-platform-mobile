package com.yehorsk.medical_platform_mobile.core.domain.model

data class Appointment(
    val id: Int,
    val datetime: String,
    val status: AppointmentStatus,
    val note: String? = null,
    val createdAt: String,
    val updatedAt: String,
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