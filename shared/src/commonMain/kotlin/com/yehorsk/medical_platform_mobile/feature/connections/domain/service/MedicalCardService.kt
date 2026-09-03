package com.yehorsk.medical_platform_mobile.feature.connections.domain.service

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.MedicalCard

interface MedicalCardService {

    suspend fun getPatientById(patientId: String): Result<ApiResponseWithData<MedicalCard>, DataError.Remote>

}