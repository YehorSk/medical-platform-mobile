package com.yehorsk.medical_platform_mobile.core.data.network.dto.ws

import kotlinx.serialization.Serializable

@Serializable
data class WebSocketMessageDto(
    val type: String,
    val payload: String
)