package com.yehorsk.medical_platform_mobile.core.data.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseWithData<T>(
    val data: T,
    val message: String = ""
)
