package com.yehorsk.medical_platform_mobile.feature.settings.data.mappers

import com.yehorsk.medical_platform_mobile.core.data.mappers.enumOrUnknown
import com.yehorsk.medical_platform_mobile.core.data.mappers.toAuditLogOutcome
import com.yehorsk.medical_platform_mobile.core.data.mappers.toInstantOrNull
import com.yehorsk.medical_platform_mobile.feature.settings.data.dto.AuthEventDto
import com.yehorsk.medical_platform_mobile.feature.settings.domain.model.AuthEvent
import com.yehorsk.medical_platform_mobile.feature.settings.domain.model.AuthEventType
import com.yehorsk.medical_platform_mobile.feature.settings.domain.model.AuthFailureReason

fun AuthEventDto.toDomain(): AuthEvent =
    AuthEvent(
        id = id,
        type = enumOrUnknown(type, AuthEventType.UNKNOWN),
        outcome = outcome.toAuditLogOutcome(),
        failureReason = failureReason?.let { enumOrUnknown(it, AuthFailureReason.UNKNOWN) },
        ipAddress = ipAddress?.takeIf { it.isNotBlank() },
        userAgent = userAgent?.takeIf { it.isNotBlank() },
        createdAt = createdAt.toInstantOrNull()
    )
