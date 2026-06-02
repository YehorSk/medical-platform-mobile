package com.yehorsk.medical_platform_mobile.core.domain.model

data class Doctor(
    val id: Int,
    val userId: Int,
    val approved: Boolean = false,
    val approvedAt: String? = null,
    val approvedBy: Int? = null
)