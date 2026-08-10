package com.yehorsk.medical_platform_mobile.feature.appointments.domain.model

import com.yehorsk.medical_platform_mobile.core.domain.model.DaySchedule
import com.yehorsk.medical_platform_mobile.core.domain.model.Doctor

data class DoctorSchedule(
    val doctor: Doctor,
    val daySchedule: List<DaySchedule>
)