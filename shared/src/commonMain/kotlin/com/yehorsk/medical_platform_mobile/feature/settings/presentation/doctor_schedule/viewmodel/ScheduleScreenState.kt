package com.yehorsk.medical_platform_mobile.feature.settings.presentation.doctor_schedule.viewmodel

import androidx.compose.runtime.Stable
import com.yehorsk.medical_platform_mobile.core.domain.model.WeekDay
import java.time.LocalTime

data class DoctorScheduleUiState(
    val schedule: List<DayScheduleUiState> = defaultWeekSchedule(),
    val isLoading: Boolean = false,
)

@Stable
data class DayScheduleUiState(
    val weekDay: WeekDay,
    val isWorkingDay: Boolean = false,
    val startTime: LocalTime? = LocalTime.of(9, 0),
    val endTime: LocalTime? = LocalTime.of(17, 0),
    val slotDurationMinutes: Int = 30,
    val breakBetweenMinutes: Int = 0,
    val hasBreak: Boolean = false,
    val lunchStart: LocalTime? = null,
    val lunchEnd: LocalTime? = null,
)

val WEEKEND = setOf(WeekDay.SATURDAY, WeekDay.SUNDAY)

val WEEKDAYS = setOf(
    WeekDay.MONDAY, WeekDay.TUESDAY, WeekDay.WEDNESDAY, WeekDay.THURSDAY, WeekDay.FRIDAY,
)

fun defaultWeekSchedule(): List<DayScheduleUiState> =
    WeekDay.entries.dropLast(1).map { day ->
        DayScheduleUiState(weekDay = day, isWorkingDay = day !in WEEKEND)
    }