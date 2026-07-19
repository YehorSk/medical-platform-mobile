package com.yehorsk.medical_platform_mobile.core.data.network

import com.yehorsk.medical_platform_mobile.core.data.mappers.toSpecialization
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.SpecializationDto
import com.yehorsk.medical_platform_mobile.core.domain.model.Specialization
import com.yehorsk.medical_platform_mobile.core.domain.repository.SpecializationService
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.core.util.map
import io.ktor.client.HttpClient

class SpecializationServiceImpl(
    private val httpClient: HttpClient
): SpecializationService {

    override suspend fun getAll(): Result<ApiResponseWithData<List<Specialization>>, DataError.Remote> {
        return httpClient.get<ApiResponseWithData<List<SpecializationDto>>>(
            route = "/specializations"
        ).map { response ->
            ApiResponseWithData(
                data = response.data.map {
                    it.toSpecialization()
                },
                message = response.message
            )
        }
    }

}