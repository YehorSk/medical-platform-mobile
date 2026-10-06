package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.viewmodel

sealed interface ChatDetailAction {
    data class OnSelectChat(val chatId: String?) : ChatDetailAction
    data class OnMessageChange(val text: String) : ChatDetailAction
    data object OnSendMessageClick : ChatDetailAction
    data object OnRetryClick : ChatDetailAction
    data class OnFirstVisibleIndexChanged(val index: Int) : ChatDetailAction
}