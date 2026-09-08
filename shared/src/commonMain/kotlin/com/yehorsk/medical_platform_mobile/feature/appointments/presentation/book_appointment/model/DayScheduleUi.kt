package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.model

import kotlinx.datetime.DayOfWeek

data class DayScheduleUi(
    val startTime: String,
    val endTime: String,
    val weekday: DayOfWeek?,
    val isWorkingDay: Boolean,
)