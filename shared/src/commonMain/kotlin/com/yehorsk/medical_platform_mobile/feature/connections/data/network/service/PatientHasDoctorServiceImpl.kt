package com.yehorsk.medical_platform_mobile.feature.connections.data.network.service

import com.yehorsk.medical_platform_mobile.core.data.mappers.toPatientHasDoctor
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.PatientHasDoctorDto
import com.yehorsk.medical_platform_mobile.core.data.network.get
import com.yehorsk.medical_platform_mobile.core.domain.model.PatientHasDoctor
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.request.UserOrResIdRequest
import com.yehorsk.medical_platform_mobile.feature.connections.domain.service.PatientHasDoctorService
import io.ktor.client.HttpClient
import com.yehorsk.medical_platform_mobile.core.data.network.post
import com.yehorsk.medical_platform_mobile.core.util.map
import com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.mappers.toDomain
import com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.response.PatientHasDoctorWithoutDoctorResponse
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.PatientHasDoctorWithoutDoctor

class PatientHasDoctorServiceImpl(
    private val httpClient: HttpClient
): PatientHasDoctorService {

    override suspend fun patientGiveAccessToDoctor(request: UserOrResIdRequest): Result<ApiResponseWithData<PatientHasDoctor>, DataError.Remote> {
        return requestOrUpdateAccess("/patient-doctor-access/give-access", request)
    }

    override suspend fun doctorRequestPatient(request: UserOrResIdRequest): Result<ApiResponseWithData<PatientHasDoctor>, DataError.Remote> {
        return requestOrUpdateAccess("/patient-doctor-access/request/patient", request)
    }

    override suspend fun approveAccess(request: UserOrResIdRequest): Result<ApiResponseWithData<PatientHasDoctor>, DataError.Remote> {
        return requestOrUpdateAccess("/patient-doctor-access/approve", request)
    }

    override suspend fun rejectAccess(request: UserOrResIdRequest): Result<ApiResponseWithData<PatientHasDoctor>, DataError.Remote> {
        return requestOrUpdateAccess("/patient-doctor-access/reject", request)
    }

    override suspend fun revokeAccess(request: UserOrResIdRequest): Result<ApiResponseWithData<PatientHasDoctor>, DataError.Remote> {
        return requestOrUpdateAccess("/patient-doctor-access/revoke", request)
    }

    override suspend fun getMyPatients(): Result<ApiResponseWithData<List<PatientHasDoctorWithoutDoctor>>, DataError.Remote> {
        return httpClient.get<ApiResponseWithData<List<PatientHasDoctorWithoutDoctorResponse>>>(
            route = "/patient-doctor-access/my-patients"
        ).map { response ->
            ApiResponseWithData(
                data = response.data.map {
                    it.toDomain()
                },
                message = response.message
            )
        }
    }

    private suspend fun requestOrUpdateAccess(route: String, request: UserOrResIdRequest): Result<ApiResponseWithData<PatientHasDoctor>, DataError.Remote> {
        return httpClient.post<UserOrResIdRequest, ApiResponseWithData<PatientHasDoctorDto>>(
            route = route,
            body = request
        ).map { response ->
            ApiResponseWithData(
                data = response.data.toPatientHasDoctor(),
                message = response.message
            )
        }
    }

}