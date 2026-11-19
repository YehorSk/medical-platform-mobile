package com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.mappers

import com.yehorsk.medical_platform_mobile.core.data.mappers.enumOrUnknown
import com.yehorsk.medical_platform_mobile.core.data.mappers.toAuditLogOutcome
import com.yehorsk.medical_platform_mobile.core.data.mappers.toInstantOrNull
import com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.response.AuditLogActorDto
import com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.response.AuditLogDto
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLog
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLogActor
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLogActorRole

fun AuditLogDto.toDomain(): AuditLog =
    AuditLog(
        id = id,
        action = action.uppercase(),
        outcome = outcome.toAuditLogOutcome(),
        detail = detail,
        createdAt = createdAt.toInstantOrNull(),
        actor = actor.toDomain()
    )

fun AuditLogActorDto.toDomain(): AuditLogActor =
    AuditLogActor(
        id = id,
        doctorId = doctorId?.takeIf { it.isNotBlank() },
        name = name,
        role = enumOrUnknown(role, AuditLogActorRole.UNKNOWN)
    )
