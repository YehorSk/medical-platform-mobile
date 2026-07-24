package com.yehorsk.medical_platform_mobile.feature.connections.domain.models.request

import kotlinx.serialization.Serializable

@Serializable
data class UserOrResIdRequest(
    val id: String
)
