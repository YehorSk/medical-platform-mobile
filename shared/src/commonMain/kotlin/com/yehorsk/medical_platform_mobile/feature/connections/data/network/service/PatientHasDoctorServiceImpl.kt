package com.yehorsk.medical_platform_mobile.feature.connections.data.network.service

import com.yehorsk.medical_platform_mobile.core.data.mappers.toPatientHasDoctor
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.PatientHasDoctorDto
import com.yehorsk.medical_platform_mobile.core.domain.model.PatientHasDoctor
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.request.UserIdRequest
import com.yehorsk.medical_platform_mobile.feature.connections.domain.service.PatientHasDoctorService
import io.ktor.client.HttpClient
import com.yehorsk.medical_platform_mobile.core.data.network.post
import com.yehorsk.medical_platform_mobile.core.util.map

class PatientHasDoctorServiceImpl(
    private val httpClient: HttpClient
): PatientHasDoctorService {

    override suspend fun patientGiveAccessToDoctor(request: UserIdRequest): Result<ApiResponseWithData<PatientHasDoctor>, DataError.Remote> {
        return httpClient.post<UserIdRequest, ApiResponseWithData<PatientHasDoctorDto>>(
            route = "/patient-doctor-access/give-access",
            body = request
        ).map { response ->
            ApiResponseWithData(
                data = response.data.toPatientHasDoctor(),
                message = response.message
            )
        }
    }

}