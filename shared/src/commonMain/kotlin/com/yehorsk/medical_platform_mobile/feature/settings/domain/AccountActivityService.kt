package com.yehorsk.medical_platform_mobile.feature.settings.domain

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.PagedResponseDto
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.settings.domain.model.AuthEvent

interface AccountActivityService {
    suspend fun getMyAuthEvents(page: Int, size: Int): Result<PagedResponseDto<AuthEvent>, DataError.Remote>
}
