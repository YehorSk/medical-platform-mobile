package com.yehorsk.medical_platform_mobile.feature.connections.data.network.service

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.data.network.get
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.core.util.map
import com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.mappers.toDomain
import com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.response.MedicalCardResponseDto
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.MedicalCard
import com.yehorsk.medical_platform_mobile.feature.connections.domain.service.MedicalCardService
import io.ktor.client.HttpClient

class MedicalCardServiceImpl(
    private val httpClient: HttpClient
): MedicalCardService {

    override suspend fun getPatientById(patientId: String): Result<ApiResponseWithData<MedicalCard>, DataError.Remote> {
        return httpClient.get<ApiResponseWithData<MedicalCardResponseDto>>(
            route = "/medical-cards/$patientId"
        ).map { response ->
            ApiResponseWithData(
                data = response.data.toDomain(),
                message = response.message
            )
        }
    }

}