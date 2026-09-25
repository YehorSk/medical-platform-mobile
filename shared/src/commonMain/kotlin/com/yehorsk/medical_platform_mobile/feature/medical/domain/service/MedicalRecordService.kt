package com.yehorsk.medical_platform_mobile.feature.medical.domain.service

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseDto
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.medical.data.dto.request.CreateMedicalRecordRequestDto

interface MedicalRecordService {

    suspend fun createMedicalRecord(request: CreateMedicalRecordRequestDto): Result<ApiResponseDto, DataError.Remote>

}