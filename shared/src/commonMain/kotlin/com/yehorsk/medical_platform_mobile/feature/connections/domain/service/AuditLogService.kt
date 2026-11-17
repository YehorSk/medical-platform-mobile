package com.yehorsk.medical_platform_mobile.feature.connections.domain.service

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.PagedResponseDto
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLog

interface AuditLogService {
    suspend fun getMyAuditLogs(page: Int, size: Int): Result<PagedResponseDto<AuditLog>, DataError.Remote>
}
