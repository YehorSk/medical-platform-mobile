package com.yehorsk.medical_platform_mobile.core.data.network.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterDeviceRequestDto(
    val token: String,
    val platform: DevicePlatformDto
)

@Serializable
enum class DevicePlatformDto {
    ANDROID
}
