package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.data.network.ConnectivityObserver
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.domain.repository.SessionStorage
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.viewmodel.BookAppointmentState
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Conversation
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Message
import com.yehorsk.medical_platform_mobile.feature.chat.domain.service.ChatConnectionClient
import com.yehorsk.medical_platform_mobile.feature.chat.domain.service.ConversationService
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class ChatListViewModel(
    private val mainLogger: MainLogger,
    private val connectivityObserver: ConnectivityObserver,
    private val conversationService: ConversationService,
    private val sessionStorage: SessionStorage,
    private val connectionClient: ChatConnectionClient
): ViewModel() {

    private var hasLoadedInitialData = false
    private var pollingJob: Job? = null

    private val _uiState = MutableStateFlow(ChatListState())
    val uiState = _uiState
        .onStart {
            if (!hasLoadedInitialData) {
                getConversations()
                hasLoadedInitialData = true
            }
            startPolling()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ChatListState()
        )

    init {
        observeAuthData()
        observeConnectivity()
        observeIncomingMessages()
    }

    fun onAction(action: ChatListAction) {
        when (action) {
            ChatListAction.Refresh -> getConversations()
            is ChatListAction.OnConversationClick -> onConversationClick(action.conversation)
        }
    }

    private fun observeAuthData() {
        viewModelScope.launch {
            sessionStorage.observeAuthData()
                .collect { authData ->
                    _uiState.update {
                        it.copy(authData = authData)
                    }
                }
        }
    }

    private fun getConversations() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            conversationService
                .getAllConversations()
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(conversations = response.data, isLoading = false)
                    }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false) }
                    SnackbarController.sendEvent(SnackbarEvent(error = error))
                }
        }
    }

    private fun startPolling() {
        if (pollingJob?.isActive == true) return

        pollingJob = viewModelScope.launch {
            connectivityObserver.isConnected
                .distinctUntilChanged()
                .collectLatest { connected ->
                    if (!connected) return@collectLatest

                    while (true) {
                        delay(POLL_INTERVAL_MS.milliseconds)
                        conversationService
                            .getAllConversations()
                            .onSuccess { response ->
                                _uiState.update { it.copy(conversations = response.data) }
                            }
                            .onFailure { error ->
                                mainLogger.debug("Polling failed: $error")
                            }
                        currentCoroutineContext().ensureActive()
                    }
                }
        }
    }

    private fun onConversationClick(conversation: Conversation) {
    }

    // New messages (including the echo of our own) move their conversation to the top with a fresh preview
    private fun observeIncomingMessages() {
        connectionClient.chatMessages
            .onEach { message ->
                val isKnown = _uiState.value.conversations.any { it.id == message.conversationId }
                if (!isKnown) {
                    // A conversation we haven't loaded yet, e.g. a newly created one
                    getConversations()
                    return@onEach
                }
                _uiState.update { state ->
                    state.copy(conversations = state.conversations.withNewMessage(message))
                }
            }
            .launchIn(viewModelScope)
    }

    private fun List<Conversation>.withNewMessage(message: Message): List<Conversation> {
        val conversation = first { it.id == message.conversationId }
        val previous = conversation.lastMessage
        // Ignore duplicates and messages older than the current preview
        if (previous != null && (previous.id == message.id || previous.createdAt > message.createdAt)) {
            return this
        }
        val updated = conversation.copy(lastMessage = message, lastMessageAt = message.createdAt)
        return listOf(updated) + filter { it.id != message.conversationId }
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

    companion object {
        // Backup for when the socket is down; live updates come from chatMessages
        private const val POLL_INTERVAL_MS = 30_000L
    }
}