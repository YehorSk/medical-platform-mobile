package com.yehorsk.medical_platform_mobile.feature.appointments.domain.mappers

import com.yehorsk.medical_platform_mobile.core.data.mappers.toDayOfWeek
import com.yehorsk.medical_platform_mobile.core.domain.model.DaySchedule
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.model.DayScheduleUi

fun DaySchedule.toDayScheduleUi(): DayScheduleUi{
    return DayScheduleUi(
        weekday = weekday.toDayOfWeek(),
        isWorkingDay = isWorkingDay
    )
}