package com.yehorsk.medical_platform_mobile.core.domain.model

import kotlin.time.Instant

data class Doctor(
    val id: String,
    val licenseNumber: String,
    val user: User? = null,
    val approvedBy: User? = null,
    val approved: Boolean = false,
    val description: String = "",
    val specialization: Specialization? = null,
    val schedules: List<Schedule> = emptyList(),
    val workplace: Workplace? = null,
    val createdAt: Instant,
    val updatedAt: Instant,
    val approvedAt: Instant,
    val currentPatientHasDoctor: Boolean
)