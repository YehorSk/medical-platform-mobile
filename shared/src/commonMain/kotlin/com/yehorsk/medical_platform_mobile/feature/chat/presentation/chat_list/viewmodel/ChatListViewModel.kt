package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.domain.model.Conversation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ChatListState())
    val uiState: StateFlow<ChatListState> = _uiState.asStateFlow()

    init {
        getConversations()
    }

    fun onAction(action: ChatListAction) {
        when (action) {
            ChatListAction.Refresh -> getConversations()
            is ChatListAction.OnConversationClick -> onConversationClick(action.conversation)
        }
    }

    private fun getConversations() {
        viewModelScope.launch {
//            _uiState.update { it.copy(isLoading = true, error = null) }
//            try {
//
//            } catch (e: Exception) {
//                _uiState.update { it.copy(error = e.message, isLoading = false) }
//            }
        }
    }

    private fun onConversationClick(conversation: Conversation) {
    }
}