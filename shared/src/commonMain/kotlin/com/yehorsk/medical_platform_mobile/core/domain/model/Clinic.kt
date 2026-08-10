package com.yehorsk.medical_platform_mobile.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Clinic(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val city: String
)
