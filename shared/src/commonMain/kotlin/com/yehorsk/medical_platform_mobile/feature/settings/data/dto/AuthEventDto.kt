package com.yehorsk.medical_platform_mobile.feature.settings.data.dto

import kotlinx.serialization.Serializable

/** One entry of GET /audit-logs/me/auth (the user's own logins, logouts, password resets) */
@Serializable
data class AuthEventDto(
    val id: String,
    // Enums kept as String so a new backend value doesn't break parsing of the whole page
    val type: String,
    val outcome: String,
    val failureReason: String? = null,
    val ipAddress: String? = null,
    val userAgent: String? = null,
    val createdAt: String
)
