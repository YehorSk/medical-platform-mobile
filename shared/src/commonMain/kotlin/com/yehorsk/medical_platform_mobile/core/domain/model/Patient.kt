package com.yehorsk.medical_platform_mobile.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Patient(
    val id: Int,
    val userId: Int,
    val phone: String? = null,
    val address: String? = null,
    val bloodType: String? = null
)