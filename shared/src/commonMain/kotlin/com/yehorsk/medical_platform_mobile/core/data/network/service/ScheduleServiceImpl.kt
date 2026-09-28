package com.yehorsk.medical_platform_mobile.core.data.network.service

import com.yehorsk.medical_platform_mobile.core.data.mappers.toDoctor
import com.yehorsk.medical_platform_mobile.core.data.mappers.toSchedule
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.data.network.get
import com.yehorsk.medical_platform_mobile.core.domain.service.ScheduleService
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.core.util.map
import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.response.AvailableTimesResponseDto
import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.response.DoctorScheduleResponseDto
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.DoctorSchedule
import io.ktor.client.HttpClient

class ScheduleServiceImpl(
    private val httpClient: HttpClient
): ScheduleService {

    override suspend fun getSchedule(doctorId: String): com.yehorsk.medical_platform_mobile.core.util.Result<ApiResponseWithData<DoctorSchedule>, DataError.Remote> {
        return httpClient.get<ApiResponseWithData<DoctorScheduleResponseDto>>(
            route = "/schedules/$doctorId"
        ).map { response ->
            ApiResponseWithData(
                data = DoctorSchedule(
                    doctor = response.data.doctor.toDoctor(),
                    daySchedule = response.data.daySchedule.map { it.toSchedule() }
                ),
                message = response.message
            )
        }
    }

    override suspend fun getScheduleAvailableTimes(doctorId: String, date: String): Result<ApiResponseWithData<AvailableTimesResponseDto>, DataError.Remote> {
        return httpClient.get<ApiResponseWithData<AvailableTimesResponseDto>>(
            route = "/schedules/$doctorId/available-times",
            queryParams = mapOf(
                "date" to date
            )
        )
    }

}