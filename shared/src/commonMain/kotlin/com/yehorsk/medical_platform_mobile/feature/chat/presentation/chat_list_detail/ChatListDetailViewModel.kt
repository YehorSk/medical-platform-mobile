package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list_detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ChatListDetailState(
    val selectedChatId: String? = null
)

sealed interface ChatListDetailAction {
    data class OnSelectChat(val chatId: String?) : ChatListDetailAction
}

class ChatListDetailViewModel : ViewModel() {

    private val _state = MutableStateFlow(ChatListDetailState())
    val state = _state.asStateFlow()

    fun onAction(action: ChatListDetailAction) {
        when (action) {
            is ChatListDetailAction.OnSelectChat -> _state.update { it.copy(selectedChatId = action.chatId) }
        }
    }
}
