package com.yehorsk.medical_platform_mobile.core.data.network.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequestDto(
    val refreshToken: String
)