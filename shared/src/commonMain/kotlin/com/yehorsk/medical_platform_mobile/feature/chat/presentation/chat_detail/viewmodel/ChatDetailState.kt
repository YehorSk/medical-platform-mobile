package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.viewmodel

import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Conversation
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
    val isNearBottom: Boolean = false,
)
