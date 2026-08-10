package com.yehorsk.medical_platform_mobile.feature.appointments.domain

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.domain.model.DaySchedule
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.DoctorSchedule

interface ScheduleService {

    suspend fun getSchedule(doctorId: String): Result<ApiResponseWithData<DoctorSchedule>, DataError.Remote>

}