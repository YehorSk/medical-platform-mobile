package com.yehorsk.medical_platform_mobile.core.domain.model

data class Doctor(
    val id: Int,
    val userId: Int,
    val approved: Boolean = false,
    val approvedAt: String? = null,
    val approvedBy: Int? = null,
    val workplace: Workplace? = null,
    val specialization: Specialization? = null
)

data class Specialization(
    val id: String,
    val name: String
)

data class Workplace(
    val id: Int,
    val name: String,
    val address: String,
    val phone: String,
    val city: String
)