package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.viewmodel

import com.yehorsk.medical_platform_mobile.core.domain.model.Conversation

data class ChatListState(
    val conversations: List<Conversation> = com.yehorsk.medical_platform_mobile.util.conversations,
    val isLoading: Boolean = false,
    val error: String? = null
)
