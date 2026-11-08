package com.yehorsk.medical_platform_mobile.core.data.network.dto.request

import kotlinx.serialization.Serializable

// PUT /schedules/me replaces the whole week, so every weekday is sent
@Serializable
data class UpdateScheduleRequestDto(
    val schedules: List<DayScheduleRequestDto>
)

@Serializable
data class DayScheduleRequestDto(
    val weekday: String,
    val isWorkingDay: Boolean,
    val startTime: String? = null,
    val endTime: String? = null,
    val lunchStart: String? = null,
    val lunchEnd: String? = null,
    val slotDurationMinutes: Int = 30,
    val breakBetweenMinutes: Int = 0
)
