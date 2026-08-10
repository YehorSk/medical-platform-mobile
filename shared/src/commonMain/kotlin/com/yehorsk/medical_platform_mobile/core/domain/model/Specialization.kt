package com.yehorsk.medical_platform_mobile.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Specialization(
    val id: String,
    val name: String
)
