package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.viewmodel

import com.yehorsk.medical_platform_mobile.feature.auth.domain.models.AuthData
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Conversation
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.ConversationBlockReason
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.model.MessageUi
import com.yehorsk.medical_platform_mobile.util.UiText

data class ChatDetailState(
    val conversation: Conversation ?= null,
    val isLoading: Boolean = false,
    val messages: List<MessageUi> = emptyList(),
    val error: UiText? = null,
    val canSendMessage: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val paginationError: UiText? = null,
    val endReached: Boolean = false,
    val message: String = "",
    val localUserId: String? = null,
    // Non-null when the user can't write here (revoked / not approved / unavailable); history stays visible
    val blockReason: ConversationBlockReason? = null,
    val inputNotice: UiText? = null,
    val isMessageTooLong: Boolean = false,
    // Socket not connected (offline / reconnecting after token renewal); sends are queued meanwhile
    val isConnecting: Boolean = false
)
