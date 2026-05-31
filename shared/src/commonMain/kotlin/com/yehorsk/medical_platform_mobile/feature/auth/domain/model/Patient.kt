package com.yehorsk.medical_platform_mobile.feature.auth.domain.model

data class Patient(
    val id: Int,
    val userId: Int,
    val phone: String? = null,
    val address: String? = null,
    val bloodType: String? = null
)