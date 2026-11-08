package com.yehorsk.medical_platform_mobile.feature.settings.presentation.doctor_schedule.viewmodel

import com.yehorsk.medical_platform_mobile.core.data.network.dto.request.DayScheduleRequestDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.request.UpdateScheduleRequestDto
import com.yehorsk.medical_platform_mobile.core.domain.model.DaySchedule
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

// Days the server has no entry for are days off
fun List<DaySchedule>.toWeekUiState(): List<DayScheduleUiState> {
    val byDay = associateBy { it.weekday }
    return defaultWeekSchedule().map { default ->
        byDay[default.weekDay]?.toUiState() ?: default.copy(isWorkingDay = false)
    }
}

fun DaySchedule.toUiState(): DayScheduleUiState {
    val lunchStart = lunchStart?.let(LocalTime::parse)
    val lunchEnd = lunchEnd?.let(LocalTime::parse)
    return DayScheduleUiState(
        weekDay = weekday,
        isWorkingDay = isWorkingDay,
        startTime = LocalTime.parse(startTime),
        endTime = LocalTime.parse(endTime),
        slotDurationMinutes = slotDurationMinutes,
        breakBetweenMinutes = breakBetweenMinutes,
        hasBreak = lunchStart != null && lunchEnd != null,
        lunchStart = lunchStart,
        lunchEnd = lunchEnd,
    )
}

fun List<DayScheduleUiState>.toUpdateScheduleRequest() = UpdateScheduleRequestDto(
    schedules = map { it.toRequestDto() }
)

fun DayScheduleUiState.toRequestDto(): DayScheduleRequestDto {
    if (!isWorkingDay) {
        return DayScheduleRequestDto(weekday = weekDay.name, isWorkingDay = false)
    }
    // Lunch is all-or-nothing on the server
    val sendLunch = hasBreak && lunchStart != null && lunchEnd != null
    return DayScheduleRequestDto(
        weekday = weekDay.name,
        isWorkingDay = true,
        startTime = startTime?.format(timeFormatter),
        endTime = endTime?.format(timeFormatter),
        lunchStart = if (sendLunch) lunchStart?.format(timeFormatter) else null,
        lunchEnd = if (sendLunch) lunchEnd?.format(timeFormatter) else null,
        slotDurationMinutes = slotDurationMinutes,
        breakBetweenMinutes = breakBetweenMinutes,
    )
}
