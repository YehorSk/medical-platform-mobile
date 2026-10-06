package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.viewmodel

import com.yehorsk.medical_platform_mobile.util.UiText

sealed interface ChatDetailEvent {
    data object OnChatLeft: ChatDetailEvent
    data class OnError(val error: UiText): ChatDetailEvent
    data object OnNewMessage: ChatDetailEvent
}