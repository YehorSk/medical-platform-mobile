package com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.mappers

import com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.response.AuditLogActorDto
import com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.response.AuditLogDto
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLog
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLogAction
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLogActor
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLogActorRole
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLogOutcome
import kotlin.time.Instant

fun AuditLogDto.toDomain(): AuditLog =
    AuditLog(
        id = id,
        action = enumOrUnknown(action, AuditLogAction.UNKNOWN),
        outcome = enumOrUnknown(outcome, AuditLogOutcome.UNKNOWN),
        detail = detail,
        createdAt = runCatching { Instant.parse(createdAt) }.getOrNull(),
        actor = actor.toDomain()
    )

fun AuditLogActorDto.toDomain(): AuditLogActor =
    AuditLogActor(
        id = id,
        name = name,
        role = enumOrUnknown(role, AuditLogActorRole.UNKNOWN),
        isMe = isMe
    )

private inline fun <reified E : Enum<E>> enumOrUnknown(value: String, unknown: E): E =
    enumValues<E>().find { it.name.equals(value, ignoreCase = true) } ?: unknown
