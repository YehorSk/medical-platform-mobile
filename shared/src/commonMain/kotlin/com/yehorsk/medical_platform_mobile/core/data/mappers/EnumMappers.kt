package com.yehorsk.medical_platform_mobile.core.data.mappers

import com.yehorsk.medical_platform_mobile.core.domain.model.AuditLogOutcome
import kotlin.time.Instant

inline fun <reified E : Enum<E>> enumOrUnknown(value: String?, unknown: E): E =
    enumValues<E>().find { it.name.equals(value, ignoreCase = true) } ?: unknown

fun String.toAuditLogOutcome(): AuditLogOutcome = enumOrUnknown(this, AuditLogOutcome.UNKNOWN)

fun String.toInstantOrNull(): Instant? = runCatching { Instant.parse(this) }.getOrNull()
