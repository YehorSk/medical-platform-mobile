package com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.response

import kotlinx.serialization.Serializable

/** One entry of GET /audit-logs/me ("who accessed my medical card") */
@Serializable
data class AuditLogDto(
    val id: String,
    // Kept as String: the backend enum also has account values that never appear in this feed
    val action: String,
    val outcome: String,
    val detail: String = "",
    val createdAt: String,
    val actor: AuditLogActorDto
)

/** `isMe` is still sent but always false now, so it's not mapped (ignoreUnknownKeys drops it) */
@Serializable
data class AuditLogActorDto(
    val id: String,
    val doctorId: String? = null,
    val name: String,
    val role: String
)
