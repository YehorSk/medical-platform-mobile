package com.yehorsk.medical_platform_mobile.feature.settings.domain.model

import com.yehorsk.medical_platform_mobile.core.domain.model.AuditLogOutcome
import kotlin.time.Instant

data class AuthEvent(
    val id: String,
    val type: AuthEventType,
    val outcome: AuditLogOutcome,
    val failureReason: AuthFailureReason?,
    val ipAddress: String?,
    /** Raw User-Agent header */
    val userAgent: String?,
    val createdAt: Instant?
)

enum class AuthEventType {
    LOGIN, LOGOUT, PASSWORD_RESET_REQUESTED, PASSWORD_RESET_CODE_CHECK, PASSWORD_RESET, UNKNOWN
}

enum class AuthFailureReason {
    WRONG_PASSWORD, EMAIL_NOT_VERIFIED, DOCTOR_NOT_APPROVED,
    INVALID_RESET_CODE, EXPIRED_RESET_CODE, TOO_MANY_ATTEMPTS, UNKNOWN
}
