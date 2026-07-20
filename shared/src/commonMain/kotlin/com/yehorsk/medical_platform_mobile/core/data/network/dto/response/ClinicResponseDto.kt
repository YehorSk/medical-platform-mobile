package com.yehorsk.medical_platform_mobile.core.data.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ClinicResponseDto(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val city: String
)
