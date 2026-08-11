package com.yehorsk.medical_platform_mobile.feature.appointments.domain

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.request.CreateAppointmentRequestDto
import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.request.UpdateAppointmentStatusRequestDto
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment

interface AppointmentService {

    suspend fun createAppointment(request: CreateAppointmentRequestDto): Result<ApiResponseWithData<Appointment>, DataError.Remote>

    suspend fun deleteAppointment(appointmentId: String): Result<ApiResponseDto, DataError.Remote>

    suspend fun getAppointmentById(appointmentId: String): Result<ApiResponseWithData<Appointment>, DataError.Remote>

    suspend fun updateAppointmentStatus(request: UpdateAppointmentStatusRequestDto): Result<ApiResponseWithData<Appointment>, DataError.Remote>

    suspend fun getMyAppointments(): Result<ApiResponseWithData<List<Appointment>>, DataError.Remote>

}