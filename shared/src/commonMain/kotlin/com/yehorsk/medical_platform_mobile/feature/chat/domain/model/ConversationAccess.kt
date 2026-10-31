package com.yehorsk.medical_platform_mobile.feature.chat.domain.model

import com.yehorsk.medical_platform_mobile.core.domain.model.AccessStatus
import kotlin.time.Instant

/** Patient–doctor relation behind a conversation; messages can only be sent while APPROVED. */
data class ConversationAccess(
    val doctorId: String,
    val patientId: String?,
    val status: AccessStatus,
    val createdAt: Instant?
)
