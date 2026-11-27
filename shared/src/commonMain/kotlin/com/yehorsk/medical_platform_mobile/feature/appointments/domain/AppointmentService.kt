package com.yehorsk.medical_platform_mobile.feature.appointments.domain

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.PagedResponseDto
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentsFilter
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.request.CreateAppointmentRequestDto
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentStatus

interface AppointmentService {

    suspend fun createAppointment(request: CreateAppointmentRequestDto): Result<ApiResponseWithData<Appointment>, DataError.Remote>

    suspend fun cancelAppointment(appointmentId: String): Result<ApiResponseWithData<Appointment>, DataError.Remote>

    suspend fun getAppointmentById(appointmentId: String): Result<ApiResponseWithData<Appointment>, DataError.Remote>

    suspend fun updateAppointmentStatus(
        appointmentId: String,
        status: AppointmentStatus
    ): Result<ApiResponseWithData<Appointment>, DataError.Remote>

    suspend fun getMyAppointments(
        filter: AppointmentsFilter,
        page: Int,
        size: Int
    ): Result<PagedResponseDto<Appointment>, DataError.Remote>

}