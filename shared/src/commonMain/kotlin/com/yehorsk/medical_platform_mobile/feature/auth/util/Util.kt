package com.yehorsk.medical_platform_mobile.feature.auth.util

import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import kotlin.time.Clock
import kotlin.time.Instant
import kotlinx.datetime.*
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number


fun getRole(role: String): UserRole {
    return UserRole.entries.find { it.name.equals(role, ignoreCase = true) } ?: UserRole.PATIENT
}

fun formatTimeAgo(createdAt: String): String {
    val instant = Instant.parse(createdAt)
    val createdMillis = instant.toEpochMilliseconds()
    val nowMillis = Clock.System.now().toEpochMilliseconds()
    val diff = nowMillis - createdMillis

    val minutes = diff / 1000 / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        minutes < 1 -> "just now"
        minutes < 60 -> "${minutes}m ago"
        hours < 24 -> "${hours}h ago"
        days < 7 -> "${days}d ago"
        else -> {
            val dt = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            "${dt.day}.${dt.month.number.toString().padStart(2, '0')}.${dt.year}"
        }
    }
}