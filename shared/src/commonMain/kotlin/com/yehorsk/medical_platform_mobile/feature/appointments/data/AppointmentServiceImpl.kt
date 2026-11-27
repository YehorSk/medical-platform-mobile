package com.yehorsk.medical_platform_mobile.feature.appointments.data

import com.yehorsk.medical_platform_mobile.core.data.mappers.toDoctor
import com.yehorsk.medical_platform_mobile.core.data.mappers.toSchedule
import com.yehorsk.medical_platform_mobile.core.data.network.delete
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.PagedResponseDto
import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.mappers.toGetAppointmentsWithFilterDto
import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.request.GetAppointmentsWithFilterDto
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentsFilter
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
import com.yehorsk.medical_platform_mobile.core.domain.service.ScheduleService
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentStatus
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.DoctorSchedule
import io.ktor.client.HttpClient

class AppointmentServiceImpl(
    private val httpClient: HttpClient
): AppointmentService {

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

    override suspend fun cancelAppointment(appointmentId: String): Result<ApiResponseWithData<Appointment>, DataError.Remote> {
        return httpClient.delete<ApiResponseWithData<AppointmentResponseDto>>(
            route = "appointments/$appointmentId"
        ).map { response ->
            ApiResponseWithData(
                data = response.data.toAppointment(),
                message = response.message
            )
        }
    }

    override suspend fun getAppointmentById(appointmentId: String): Result<ApiResponseWithData<Appointment>, DataError.Remote> {
        return httpClient.get<ApiResponseWithData<AppointmentResponseDto>>(
            route = "appointments/$appointmentId"
        ).map { response ->
            ApiResponseWithData(
                data = response.data.toAppointment(),
                message = response.message
            )
        }
    }

    override suspend fun updateAppointmentStatus(
        appointmentId: String,
        status: AppointmentStatus
    ): Result<ApiResponseWithData<Appointment>, DataError.Remote> {
        return httpClient.post<UpdateAppointmentStatusRequestDto, ApiResponseWithData<AppointmentResponseDto>>(
            route = "appointments/$appointmentId/status",
            body = UpdateAppointmentStatusRequestDto(
                appointmentId = appointmentId,
                status = status
            )
        ).map { response ->
            ApiResponseWithData(
                data = response.data.toAppointment(),
                message = response.message
            )
        }
    }

    override suspend fun getMyAppointments(
        filter: AppointmentsFilter,
        page: Int,
        size: Int
    ): Result<PagedResponseDto<Appointment>, DataError.Remote> {
        return httpClient.post<GetAppointmentsWithFilterDto, PagedResponseDto<AppointmentResponseDto>>(
            route = "/appointments/search",
            body = filter.toGetAppointmentsWithFilterDto(),
            queryParams = mapOf(
                "page" to page,
                "size" to size,
                // The backend ignores sortDirection from the body when paging, so it's sent here too
                "sort" to "dateTime,${filter.sortDirection.name.lowercase()}"
            )
        ).map { response ->
            PagedResponseDto(
                content = response.content.map { it.toAppointment() },
                page = response.page,
                size = response.size,
                totalElements = response.totalElements,
                totalPages = response.totalPages,
                hasNext = response.hasNext
            )
        }
    }

}