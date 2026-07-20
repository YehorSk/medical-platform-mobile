package com.yehorsk.medical_platform_mobile.core.data.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class WorkplaceResponseDto(
    val id: String,
    val roomNumber: String,
    val clinic: ClinicResponseDto
)
