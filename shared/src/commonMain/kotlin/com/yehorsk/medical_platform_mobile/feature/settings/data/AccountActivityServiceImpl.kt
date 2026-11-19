package com.yehorsk.medical_platform_mobile.feature.settings.data

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.PagedResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.get
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.core.util.map
import com.yehorsk.medical_platform_mobile.feature.settings.data.dto.AuthEventDto
import com.yehorsk.medical_platform_mobile.feature.settings.data.mappers.toDomain
import com.yehorsk.medical_platform_mobile.feature.settings.domain.AccountActivityService
import com.yehorsk.medical_platform_mobile.feature.settings.domain.model.AuthEvent
import io.ktor.client.HttpClient

class AccountActivityServiceImpl(
    private val httpClient: HttpClient
) : AccountActivityService {

    override suspend fun getMyAuthEvents(page: Int, size: Int): Result<PagedResponseDto<AuthEvent>, DataError.Remote> {
        return httpClient.get<ApiResponseWithData<PagedResponseDto<AuthEventDto>>>(
            route = "/audit-logs/me/auth",
            queryParams = mapOf(
                "page" to page.coerceAtLeast(0),
                "size" to size.coerceIn(1, 50)
            )
        ).map { response ->
            val paged = response.data
            PagedResponseDto(
                content = paged.content.map { it.toDomain() },
                page = paged.page,
                size = paged.size,
                hasNext = paged.hasNext,
                totalElements = paged.totalElements,
                totalPages = paged.totalPages
            )
        }
    }
}
