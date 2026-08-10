package com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.response

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.DayScheduleResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.DoctorResponseDto
import kotlinx.serialization.Serializable

@Serializable
data class DoctorScheduleResponseDto(
    val doctor: DoctorResponseDto,
    val daySchedule: List<DayScheduleResponseDto>
)