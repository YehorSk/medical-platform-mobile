package com.yehorsk.medical_platform_mobile.feature.connections.domain.service

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.domain.model.PatientHasDoctor
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.request.UserIdRequest

interface PatientHasDoctorService{

    suspend fun patientGiveAccessToDoctor(request: UserIdRequest): Result<ApiResponseWithData<PatientHasDoctor>, DataError.Remote>

}
