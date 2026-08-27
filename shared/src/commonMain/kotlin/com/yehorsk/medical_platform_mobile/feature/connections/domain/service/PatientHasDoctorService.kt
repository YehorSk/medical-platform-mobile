package com.yehorsk.medical_platform_mobile.feature.connections.domain.service

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.domain.model.PatientHasDoctor
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.response.PatientHasDoctorWithoutDoctorResponse
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.request.UserOrResIdRequest
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.PatientHasDoctorWithoutDoctor

interface PatientHasDoctorService{

    suspend fun patientGiveAccessToDoctor(request: UserOrResIdRequest): Result<ApiResponseWithData<PatientHasDoctor>, DataError.Remote>

    suspend fun doctorRequestPatient(request: UserOrResIdRequest): Result<ApiResponseWithData<PatientHasDoctor>, DataError.Remote>

    suspend fun approveAccess(request: UserOrResIdRequest): Result<ApiResponseWithData<PatientHasDoctor>, DataError.Remote>

    suspend fun rejectAccess(request: UserOrResIdRequest): Result<ApiResponseWithData<PatientHasDoctor>, DataError.Remote>

    suspend fun revokeAccess(request: UserOrResIdRequest): Result<ApiResponseWithData<PatientHasDoctor>, DataError.Remote>

    suspend fun getMyPatients(): Result<ApiResponseWithData<List<PatientHasDoctorWithoutDoctor>>, DataError.Remote>

}
