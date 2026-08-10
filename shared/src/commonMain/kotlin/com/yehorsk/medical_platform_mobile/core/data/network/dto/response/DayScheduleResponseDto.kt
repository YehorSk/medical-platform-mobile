package com.yehorsk.medical_platform_mobile.core.data.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class DayScheduleResponseDto(
    val weekday: String,
    val startTime: String,
    val endTime: String,
    val lunchStart: String?,
    val lunchEnd: String?,
    val isWorkingDay: Boolean,
    val slotDurationMinutes: Int,
    val breakBetweenMinutes: Int
)