package com.yehorsk.medical_platform_mobile.feature.medical.data.service

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.data.network.get
import com.yehorsk.medical_platform_mobile.core.data.network.post
import com.yehorsk.medical_platform_mobile.core.data.network.put
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.core.util.map
import com.yehorsk.medical_platform_mobile.feature.medical.data.dto.response.MedicalCardResponseDto
import com.yehorsk.medical_platform_mobile.feature.medical.data.mappers.toDomain
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.response.MedicalCard
import com.yehorsk.medical_platform_mobile.feature.medical.domain.service.MedicalCardService
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_card.viewmodel.MedicalCardForm
import io.ktor.client.HttpClient
import io.ktor.client.request.post

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

    override suspend fun getMyMedicalCard(): Result<ApiResponseWithData<MedicalCard>, DataError.Remote> {
        return httpClient.get<ApiResponseWithData<MedicalCardResponseDto>>(
            route = "/medical-cards/me"
        ).map { response ->
            ApiResponseWithData(
                data = response.data.toDomain(),
                message = response.message
            )
        }
    }

    override suspend fun updateMyMedicalCard(form: MedicalCardForm): Result<ApiResponseWithData<MedicalCard>, DataError.Remote> {
        return httpClient.put<MedicalCardForm, ApiResponseWithData<MedicalCardResponseDto>>(
            route = "/medical-cards/me",
            body = form
        ).map { response ->
            ApiResponseWithData(
                data = response.data.toDomain(),
                message = response.message
            )
        }
    }

}