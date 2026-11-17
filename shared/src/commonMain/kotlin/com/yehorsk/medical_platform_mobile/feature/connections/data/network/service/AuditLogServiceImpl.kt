package com.yehorsk.medical_platform_mobile.feature.connections.data.network.service

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.PagedResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.get
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.core.util.map
import com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.mappers.toDomain
import com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.response.AuditLogDto
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLog
import com.yehorsk.medical_platform_mobile.feature.connections.domain.service.AuditLogService
import io.ktor.client.HttpClient

class AuditLogServiceImpl(
    private val httpClient: HttpClient
) : AuditLogService {

    override suspend fun getMyAuditLogs(page: Int, size: Int): Result<PagedResponseDto<AuditLog>, DataError.Remote> {
        return httpClient.get<ApiResponseWithData<PagedResponseDto<AuditLogDto>>>(
            route = "/audit-logs/me",
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
