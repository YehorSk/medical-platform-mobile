package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.viewmodel

import com.yehorsk.medical_platform_mobile.core.domain.model.DaySchedule
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentsFilter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock

data class AppointmentsListState(
    val appointments: List<Appointment> = emptyList(),
    val isConnected: Boolean = true,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    // Doctor's weekly schedule, used by the desktop calendar for working hours and slot length
    val daySchedules: List<DaySchedule> = emptyList(),
    val viewMode: AppointmentsViewMode = AppointmentsViewMode.CALENDAR,
    val calendarMode: CalendarMode = CalendarMode.WEEK,
    // Shared by the month, week and day views, so switching keeps the same date in view
    val selectedDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    // Applied filter, used only by the list; the calendar always loads everything
    val filter: AppointmentsFilter = AppointmentsFilter(),
    // Edited in the filter sheet; copied into filter only on Apply
    val filterDraft: AppointmentsFilter = AppointmentsFilter(),
    val showFilterSheet: Boolean = false,
)

enum class AppointmentsViewMode { CALENDAR, LIST }

enum class CalendarMode { MONTH, WEEK, DAY }
