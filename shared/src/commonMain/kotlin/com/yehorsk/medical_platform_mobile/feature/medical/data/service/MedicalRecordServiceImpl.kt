package com.yehorsk.medical_platform_mobile.feature.medical.data.service

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.post
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.medical.data.dto.request.CreateMedicalRecordRequestDto
import com.yehorsk.medical_platform_mobile.feature.medical.domain.service.MedicalRecordService
import io.ktor.client.HttpClient

class MedicalRecordServiceImpl(
    private val httpClient: HttpClient
): MedicalRecordService {

    override suspend fun createMedicalRecord(request: CreateMedicalRecordRequestDto): Result<ApiResponseDto, DataError.Remote> {
        return httpClient.post<CreateMedicalRecordRequestDto, ApiResponseDto>(
            route = "/medical-records/",
            body = request
        )
    }

}