package com.yehorsk.medical_platform_mobile.core.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequestDto(
    val refreshToken: String
)