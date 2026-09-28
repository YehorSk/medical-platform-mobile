package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.data.network.ConnectivityObserver
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.AppointmentService
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
class AppointmentsListViewModel(
    private val mainLogger: MainLogger,
    private val connectivityObserver: ConnectivityObserver,
    private val appointmentService: AppointmentService,
): ViewModel(){

    private var appointmentsJob: Job? = null
    private var hasLoadedOnce: Boolean = false
    private var hasResumedOnce: Boolean = false

    private val _uiState = MutableStateFlow(AppointmentsListState())
    val uiState = _uiState
        .onStart {
            loadInitialData()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = AppointmentsListState()
        )

    init {
        observeConnectivity()
    }

    fun onAction(action: AppointmentsListAction) = Unit

    private fun observeConnectivity() {
        connectivityObserver.isConnected
            .debounce(1.seconds)
            .distinctUntilChanged()
            .onEach { connected ->
                mainLogger.debug("Connectivity = $connected")
                val wasConnected = _uiState.value.isConnected
                _uiState.update { it.copy(isConnected = connected) }
                if (connected && !wasConnected && appointmentsJob?.isActive != true) {
                    getAllAppointments(isRefresh = hasLoadedOnce)
                }
            }
            .launchIn(viewModelScope)
    }

    fun onResume() {
        if (!hasResumedOnce) {
            hasResumedOnce = true
            return
        }
        refresh()
    }

    fun refresh() {
        if (!hasLoadedOnce || appointmentsJob?.isActive == true) return
        getAllAppointments(isRefresh = true)
    }

    private fun loadInitialData() {
        if (hasLoadedOnce || appointmentsJob?.isActive == true) return
        getAllAppointments(isRefresh = false)
    }

     private fun getAllAppointments(isRefresh: Boolean){
         mainLogger.debug("getAllAppointments isRefresh=$isRefresh vm=${System.identityHashCode(this)}")
         appointmentsJob?.cancel()

         appointmentsJob = viewModelScope.launch {
            _uiState.update {
                if (isRefresh) it.copy(isRefreshing = true)
                else it.copy(isLoading = true)
            }
            appointmentService
                .getMyAppointments()
                .onSuccess { response ->
                    hasLoadedOnce = true
                    _uiState.update {
                        it.copy(
                            appointments = response.data,
                            isLoading = false,
                            isRefreshing = false,
                        )
                    }
                    mainLogger.debug("Doctors response: $response")
                }
                .onFailure { dataErrorRemote ->
                    _uiState.update {
                        it.copy(isLoading = false, isRefreshing = false)
                    }
                    SnackbarController.sendEvent(SnackbarEvent(error = dataErrorRemote))
                }
        }
    }

}