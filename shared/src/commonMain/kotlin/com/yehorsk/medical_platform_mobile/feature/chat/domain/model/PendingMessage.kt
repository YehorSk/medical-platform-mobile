package com.yehorsk.medical_platform_mobile.feature.chat.domain.model

import kotlin.time.Instant

/** A message the user sent that the server hasn't echoed back yet. */
data class PendingMessage(
    val localId: String,
    val conversationId: String,
    val content: String,
    val createdAt: Instant,
    val status: PendingMessageStatus,
    val error: ChatSendError? = null
)

enum class PendingMessageStatus {
    SENDING,
    FAILED
}

/** WebSocket ERROR codes the server sends in reply to a NEW_MESSAGE. */
enum class ChatSendError {
    CONVERSATION_NOT_ACTIVE,
    FORBIDDEN,
    INVALID_CONTENT,
    SERVER_ERROR;

    companion object {
        fun fromCode(code: String): ChatSendError = when (code) {
            "CONVERSATION_NOT_ACTIVE" -> CONVERSATION_NOT_ACTIVE
            "FORBIDDEN" -> FORBIDDEN
            "INVALID_CONTENT" -> INVALID_CONTENT
            // INVALID_JSON, INTERNAL_ERROR and anything new: worth a retry
            else -> SERVER_ERROR
        }
    }
}

/** Why the user can't write in a conversation; history stays readable. */
enum class ConversationBlockReason {
    // ACCESS_REVOKED event, or the relation is REVOKED
    ACCESS_REVOKED,
    // CONVERSATION_NOT_ACTIVE error, or the relation is PENDING / REJECTED
    NOT_APPROVED,
    // Not a participant (404 on messages, FORBIDDEN on send)
    UNAVAILABLE
}
