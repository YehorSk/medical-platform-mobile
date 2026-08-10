package com.yehorsk.medical_platform_mobile.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DaySchedule(
    val weekday: WeekDay,
    val startTime: String,
    val endTime: String,
    val lunchStart: String?,
    val lunchEnd: String?,
    val isWorkingDay: Boolean,
    val slotDurationMinutes: Int,
    val breakBetweenMinutes: Int
)

enum class WeekDay {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, UNKNOWN
}