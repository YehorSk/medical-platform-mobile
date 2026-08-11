package com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class AvailableTimesResponseDto(
    val availableTimes: List<String>
)
