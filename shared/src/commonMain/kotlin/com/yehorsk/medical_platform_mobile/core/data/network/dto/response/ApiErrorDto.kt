package com.yehorsk.medical_platform_mobile.core.data.network.dto.response

import kotlinx.serialization.Serializable

// Body of backend AppException errors
@Serializable
data class ApiErrorDto(
    val status: Int? = null,
    val errorCode: String? = null,
    val message: String? = null
)
