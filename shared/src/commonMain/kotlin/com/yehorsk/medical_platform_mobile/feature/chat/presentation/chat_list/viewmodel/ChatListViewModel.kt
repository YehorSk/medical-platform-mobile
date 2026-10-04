package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.data.network.ConnectivityObserver
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.viewmodel.BookAppointmentState
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Conversation
import com.yehorsk.medical_platform_mobile.feature.chat.domain.service.ConversationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatListViewModel(
    private val mainLogger: MainLogger,
    private val connectivityObserver: ConnectivityObserver,
    private val conversationService: ConversationService
): ViewModel() {

    private var hasLoadedInitialData = false

    private val _uiState = MutableStateFlow(ChatListState())
    val uiState = _uiState
        .onStart {
            if(!hasLoadedInitialData){
                getConversations()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ChatListState()
        )

    init {
        observeConnectivity()
    }

    fun onAction(action: ChatListAction) {
        when (action) {
            ChatListAction.Refresh -> getConversations()
            is ChatListAction.OnConversationClick -> onConversationClick(action.conversation)
        }
    }

    private fun getConversations() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            conversationService
                .getAllConversations()
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            conversations = response.data,
                            isLoading = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoading = false)
                    }

                    SnackbarController.sendEvent(
                        SnackbarEvent(error = error)
                    )
                }

        }
    }

    private fun onConversationClick(conversation: Conversation) {
    }

    private fun observeConnectivity() {
        connectivityObserver.isConnected
            .distinctUntilChanged()
            .onEach { connected ->
                mainLogger.debug("Connectivity = $connected")
                _uiState.update { it.copy(isConnected = connected) }
            }
            .launchIn(viewModelScope)
    }
}