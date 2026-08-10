package com.yehorsk.medical_platform_mobile.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Workplace(
    val id: String,
    val roomNumber: String,
    val clinic: Clinic
)