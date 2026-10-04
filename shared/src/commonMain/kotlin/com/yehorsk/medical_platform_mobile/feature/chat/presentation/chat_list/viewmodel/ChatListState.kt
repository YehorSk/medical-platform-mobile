package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.viewmodel

import com.yehorsk.medical_platform_mobile.feature.auth.domain.models.AuthData
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Conversation

data class ChatListState(
    val conversations: List<Conversation> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isConnected: Boolean = false,
    val authData: AuthData? = null,
)
