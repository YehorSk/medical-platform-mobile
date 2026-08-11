package com.yehorsk.medical_platform_mobile.feature.appointments.domain

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.domain.model.DaySchedule
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.response.AvailableTimesResponseDto
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.DoctorSchedule

interface ScheduleService {

    suspend fun getSchedule(doctorId: String): Result<ApiResponseWithData<DoctorSchedule>, DataError.Remote>

    suspend fun getScheduleAvailableTimes(doctorId: String, date: String): Result<ApiResponseWithData<AvailableTimesResponseDto>, DataError.Remote>
}