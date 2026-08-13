package com.yehorsk.medical_platform_mobile.feature.appointments.data

import com.yehorsk.medical_platform_mobile.core.data.mappers.toDoctor
import com.yehorsk.medical_platform_mobile.core.data.mappers.toSchedule
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.data.network.get
import com.yehorsk.medical_platform_mobile.core.data.network.post
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.core.util.map
import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.mappers.toAppointment
import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.request.CreateAppointmentRequestDto
import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.request.UpdateAppointmentStatusRequestDto
import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.response.AppointmentResponseDto
import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.response.AvailableTimesResponseDto
import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.response.DoctorScheduleResponseDto
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.AppointmentService
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.ScheduleService
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.DoctorSchedule
import io.ktor.client.HttpClient
import io.ktor.client.request.post

class AppointmentServiceImpl(
    private val httpClient: HttpClient
): AppointmentService, ScheduleService {

    override suspend fun createAppointment(request: CreateAppointmentRequestDto): Result<ApiResponseWithData<Appointment>, DataError.Remote> {
        return httpClient.post<CreateAppointmentRequestDto, ApiResponseWithData<AppointmentResponseDto>>(
            route = "appointments",
            body = request
        ).map { response ->
            ApiResponseWithData(
                data = response.data.toAppointment(),
                message = response.message
            )
        }
    }

    override suspend fun deleteAppointment(appointmentId: String): Result<ApiResponseDto, DataError.Remote> {
        TODO("Not yet implemented")
    }

    override suspend fun getAppointmentById(appointmentId: String): Result<ApiResponseWithData<Appointment>, DataError.Remote> {
        TODO("Not yet implemented")
    }

    override suspend fun updateAppointmentStatus(request: UpdateAppointmentStatusRequestDto): Result<ApiResponseWithData<Appointment>, DataError.Remote> {
        TODO("Not yet implemented")
    }

    override suspend fun getMyAppointments(): Result<ApiResponseWithData<List<Appointment>>, DataError.Remote> {
        return httpClient.get<ApiResponseWithData<List<AppointmentResponseDto>>>(
            route = "/appointments/my-appointments"
        ).map { response ->
            ApiResponseWithData(
                data = response.data.map { it.toAppointment() },
                message = response.message
            )
        }
    }

    override suspend fun getSchedule(doctorId: String): Result<ApiResponseWithData<DoctorSchedule>, DataError.Remote> {
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