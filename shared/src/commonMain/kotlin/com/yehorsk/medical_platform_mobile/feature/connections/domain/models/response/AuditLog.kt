package com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response

import com.yehorsk.medical_platform_mobile.core.domain.model.AuditLogOutcome
import kotlin.time.Instant

/** Someone else accessing (or trying to access) the patient's medical card */
data class AuditLog(
    val id: String,
    /** Raw backend action; see [AuditLogActions] for the values this feed uses */
    val action: String,
    val outcome: AuditLogOutcome,
    val detail: String,
    val createdAt: Instant?,
    val actor: AuditLogActor
)

data class AuditLogActor(
    /** User id */
    val id: String,
    /** Doctor id for opening the doctor's profile; null when the actor isn't a doctor */
    val doctorId: String?,
    val name: String,
    val role: AuditLogActorRole
)

object AuditLogActions {
    /** Medical card viewed */
    const val READ = "READ"
    /** Files added to a medical record */
    const val UPDATE = "UPDATE"
}

// Separate from UserRole: audit actors can also be ADMIN
enum class AuditLogActorRole { PATIENT, DOCTOR, ADMIN, UNKNOWN }
