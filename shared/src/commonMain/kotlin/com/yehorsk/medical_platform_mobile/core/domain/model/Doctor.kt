package com.yehorsk.medical_platform_mobile.core.domain.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class Doctor(
    val id: String,
    val licenseNumber: String,
    val user: User? = null,
    val approvedBy: User? = null,
    val approved: Boolean = false,
    val description: String = "",
    val specialization: Specialization? = null,
    val daySchedules: List<DaySchedule> = emptyList(),
    val workplace: Workplace? = null,
    val createdAt: Instant,
    val updatedAt: Instant,
    val approvedAt: Instant,
    val currentPatientHasDoctor: Boolean
)