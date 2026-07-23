package com.yehorsk.medical_platform_mobile.core.domain.model

data class PatientHasDoctor(
    val id: String,
    val status: AccessStatus,
    val initiatedBy: UserRole,
    val createdAt: String,
    val updatedAt: String,
)

enum class AccessStatus {
    PENDING, APPROVED, REJECTED, REVOKED, UNKNOWN
}
