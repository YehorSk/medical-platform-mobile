package com.yehorsk.medical_platform_mobile.core.data.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class SpecializationDto(
    val id: String,
    val name: String,
)
