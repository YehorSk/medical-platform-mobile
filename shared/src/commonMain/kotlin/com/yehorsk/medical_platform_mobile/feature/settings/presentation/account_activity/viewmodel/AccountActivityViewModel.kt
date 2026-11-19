package com.yehorsk.medical_platform_mobile.feature.settings.presentation.account_activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.data.network.ConnectivityObserver
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.settings.domain.AccountActivityService
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@OptIn(FlowPreview::class)
class AccountActivityViewModel(
    private val accountActivityService: AccountActivityService,
    private val mainLogger: MainLogger,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private var hasLoadedInitialData = false
    private var loadJob: Job? = null

    private val _uiState = MutableStateFlow(AccountActivityState())
    val uiState = _uiState
        .onStart {
            if (!hasLoadedInitialData) {
                getAuthEvents()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = AccountActivityState()
        )

    init {
        observeConnectivity()
    }

    fun onAction(action: AccountActivityAction) {
        when (action) {
            AccountActivityAction.OnGoBackClicked -> {}
        }
    }

    private fun observeConnectivity() {
        connectivityObserver.isConnected
            .debounce(1.seconds)
            .distinctUntilChanged()
            .drop(1)
            .onEach { connected ->
                _uiState.update { it.copy(isConnected = connected) }
                if (connected) getAuthEvents()
            }
            .launchIn(viewModelScope)
    }

    private fun getAuthEvents() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            accountActivityService
                .getMyAuthEvents(page = 0, size = MAX_ITEMS)
                .onSuccess { response ->
                    _uiState.update { it.copy(events = response.content, isLoading = false) }
                }
                .onFailure { error ->
                    mainLogger.debug("Auth events error: $error")
                    _uiState.update { it.copy(isLoading = false) }
                    SnackbarController.sendEvent(SnackbarEvent(error = error))
                }
        }
    }

    private companion object {
        // No pagination: the newest 50 entries (server max) are enough here
        const val MAX_ITEMS = 50
    }
}
