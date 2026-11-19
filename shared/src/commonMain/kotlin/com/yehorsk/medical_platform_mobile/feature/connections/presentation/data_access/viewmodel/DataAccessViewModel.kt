package com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.data.network.ConnectivityObserver
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.connections.domain.service.AuditLogService
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
class DataAccessViewModel(
    private val auditLogService: AuditLogService,
    private val mainLogger: MainLogger,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private var hasLoadedInitialData = false
    private var loadJob: Job? = null

    private val _uiState = MutableStateFlow(DataAccessState())
    val uiState = _uiState
        .onStart {
            if (!hasLoadedInitialData) {
                getAuditLogs()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = DataAccessState()
        )

    init {
        observeConnectivity()
    }

    fun onAction(action: DataAccessAction) {
        when (action) {
            DataAccessAction.OnGoBackClicked -> {}
            is DataAccessAction.OnLogClicked -> {}
        }
    }

    private fun observeConnectivity() {
        connectivityObserver.isConnected
            .debounce(1.seconds)
            .distinctUntilChanged()
            .drop(1)
            .onEach { connected ->
                _uiState.update { it.copy(isConnected = connected) }
                if (connected) getAuditLogs()
            }
            .launchIn(viewModelScope)
    }

    private fun getAuditLogs() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            auditLogService
                .getMyAuditLogs(page = 0, size = MAX_ITEMS)
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            logs = response.content,
                            hasMedicalCard = true,
                            isLoading = false
                        )
                    }
                }
                .onFailure { error ->
                    mainLogger.debug("Audit logs error: $error")
                    val noMedicalCard = error == DataError.Remote.Status.NOT_FOUND
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            hasMedicalCard = !noMedicalCard
                        )
                    }
                    if (!noMedicalCard) {
                        SnackbarController.sendEvent(SnackbarEvent(error = error))
                    }
                }
        }
    }

    private companion object {
        // Server caps size at 50; newest entries come first
        const val MAX_ITEMS = 50
    }
}
