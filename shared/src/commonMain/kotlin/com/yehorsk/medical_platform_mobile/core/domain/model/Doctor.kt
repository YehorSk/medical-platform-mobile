package com.yehorsk.medical_platform_mobile.core.domain.model

data class Doctor(
    val id: String,
    val licenseNumber: String,
    val user: User? = null,
    val approvedBy: User? = null,
    val approved: Boolean = false,
    val description: String = "",
    val specialization: Specialization? = null,
    val workplace: Workplace? = null,
    val createdAt: String,
    val updatedAt: String,
    val approvedAt: String,
    val currentPatientHasDoctor: Boolean
)