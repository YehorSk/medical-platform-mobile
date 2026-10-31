package com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.domain.repository.SessionStorage
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Conversation
import com.yehorsk.medical_platform_mobile.feature.chat.domain.service.ConversationService
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Instant

class DashboardViewModel(
    private val sessionStorage: SessionStorage,
    private val conversationService: ConversationService,
    private val logger: MainLogger
): ViewModel() {

    private var authJob: Job? = null
    private var conversationsJob: Job? = null

    private val _uiState = MutableStateFlow(DashboardState())
    val uiState = _uiState
        .onStart {
            observeAuthData()
            // Every time the home screen is shown again, so returning from a chat shows fresh order
            loadRecentConversations()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = DashboardState()
        )

    fun onAction(action: DashboardAction) = null

    private fun observeAuthData() {
        if (authJob?.isActive == true) return
        authJob = sessionStorage
            .observeAuthData()
            .onEach { authData ->
                _uiState.update {
                    it.copy(
                        user = authData?.user ?: it.user,
                        authData = authData
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadRecentConversations() {
        if (conversationsJob?.isActive == true) return
        conversationsJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoadingConversations = true) }
            conversationService
                .getAllConversations()
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            recentConversations = response.data.newestFirst().take(RECENT_CONVERSATIONS_COUNT),
                            isLoadingConversations = false
                        )
                    }
                }
                .onFailure { error ->
                    // Home screen stays quiet: the block keeps its last list (or the empty placeholder)
                    logger.debug("Dashboard conversations failed -> $error")
                    _uiState.update { it.copy(isLoadingConversations = false) }
                }
        }
    }

    // Conversations without any message yet go last
    private fun List<Conversation>.newestFirst(): List<Conversation> =
        sortedByDescending { it.lastMessageAt ?: it.lastMessage?.createdAt ?: Instant.DISTANT_PAST }

    companion object {
        const val RECENT_CONVERSATIONS_COUNT = 3
    }

}
