package com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.viewmodel

import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.feature.auth.domain.models.AuthData
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Conversation

data class DashboardState(
    val user: User ?= null,
    val isLoading: Boolean = false,
    // ConversationItem needs the local user to pick the other participant
    val authData: AuthData? = null,
    // Newest first, at most DashboardViewModel.RECENT_CONVERSATIONS_COUNT
    val recentConversations: List<Conversation> = emptyList(),
    val isLoadingConversations: Boolean = false
)
