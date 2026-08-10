package com.yehorsk.medical_platform_mobile.feature.appointments.data

import com.yehorsk.medical_platform_mobile.core.data.mappers.toDoctor
import com.yehorsk.medical_platform_mobile.core.data.mappers.toSchedule
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.DayScheduleResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.get
import com.yehorsk.medical_platform_mobile.core.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.core.domain.model.DaySchedule
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.core.util.map
import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.request.CreateAppointmentRequestDto
import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.request.UpdateAppointmentStatusRequestDto
import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.response.DoctorScheduleResponseDto
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.AppointmentService
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.ScheduleService
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.DoctorSchedule
import io.ktor.client.HttpClient

class AppointmentServiceImpl(
    private val httpClient: HttpClient
): AppointmentService, ScheduleService {
    override suspend fun createAppointment(request: CreateAppointmentRequestDto): Result<ApiResponseWithData<Appointment>, DataError.Remote> {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
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
}