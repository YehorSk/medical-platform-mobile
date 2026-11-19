package com.yehorsk.medical_platform_mobile.core.domain.model

/** Outcome of an audited action, shared by the card access and account activity feeds */
enum class AuditLogOutcome { SUCCESS, DENIED, FAILED, UNKNOWN;

    /** FAILED and DENIED may mean someone else is trying to get in, so the UI highlights them */
    val isWarning: Boolean
        get() = this == DENIED || this == FAILED
}
