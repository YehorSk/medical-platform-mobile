package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.viewmodel

import com.yehorsk.medical_platform_mobile.core.domain.model.Conversation

sealed interface ChatListAction {
    data object Refresh : ChatListAction
    data class OnConversationClick(val conversation: Conversation) : ChatListAction
}