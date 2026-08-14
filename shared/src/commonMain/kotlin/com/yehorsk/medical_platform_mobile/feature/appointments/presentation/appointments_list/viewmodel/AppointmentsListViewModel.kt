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

    private var hasLoadedInitialData = false

    init {
        observeConnectivity()
    }

    private val _uiState = MutableStateFlow(AppointmentsListState())
    val uiState = _uiState
        .onStart {
            if(!hasLoadedInitialData){
                getAllAppointments()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = AppointmentsListState()
        )

    fun onAction(action: AppointmentsListAction) = Unit

    private fun observeConnectivity() {
        connectivityObserver.isConnected
            .debounce(1.seconds)
            .distinctUntilChanged()
            .drop(1)
            .onEach { connected ->
                mainLogger.debug("Connectivity = $connected")
                _uiState.update { it.copy(isConnected = connected) }
                if(connected) {
                    mainLogger.debug("Get Appointments: wifi")
                    getAllAppointments()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getAllAppointments(){
        viewModelScope.launch {
            _uiState.update {
                it.copy( isLoading = true )
            }
            appointmentService
                .getMyAppointments()
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            appointments = response.data,
                            isLoading = false
                        )
                    }
                    mainLogger.debug("Doctors response: $response")
                }
                .onFailure { dataErrorRemote ->
                    _uiState.update { it.copy(isLoading = false) }
                    SnackbarController.sendEvent(SnackbarEvent(error = dataErrorRemote))
                }
        }
    }

}