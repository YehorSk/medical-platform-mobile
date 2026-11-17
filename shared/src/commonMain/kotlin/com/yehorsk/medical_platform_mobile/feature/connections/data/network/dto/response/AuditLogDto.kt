package com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class AuditLogDto(
    val id: String,
    val action: String,
    val outcome: String,
    val detail: String = "",
    val createdAt: String,
    val actor: AuditLogActorDto
)

@Serializable
data class AuditLogActorDto(
    val id: String,
    val name: String,
    val role: String,
    val isMe: Boolean = false
)
