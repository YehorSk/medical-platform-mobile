package com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response

import kotlin.time.Instant

data class AuditLog(
    val id: String,
    val action: AuditLogAction,
    val outcome: AuditLogOutcome,
    val detail: String,
    val createdAt: Instant?,
    val actor: AuditLogActor
)

data class AuditLogActor(
    val id: String,
    val name: String,
    val role: AuditLogActorRole,
    val isMe: Boolean
)

enum class AuditLogAction { CREATE, READ, UPDATE, DELETE, UNKNOWN }

enum class AuditLogOutcome { SUCCESS, DENIED, FAILED, UNKNOWN }

// Separate from UserRole: audit actors can also be ADMIN
enum class AuditLogActorRole { PATIENT, DOCTOR, ADMIN, UNKNOWN }
