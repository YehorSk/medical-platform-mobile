package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.data.network.ConnectivityObserver
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.AppointmentService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppointmentDetailsViewModel(
    private val mainLogger: MainLogger,
    private val connectivityObserver: ConnectivityObserver,
    private val appointmentService: AppointmentService,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var hasLoadedInitialData = false

    private val appointmentId = savedStateHandle.get<String>("appointmentId")
        ?: throw IllegalStateException("Appointment id is missing")

    private val _uiState = MutableStateFlow(AppointmentDetailsState())
    val uiState = _uiState
        .onStart {
            if(!hasLoadedInitialData){
                getAppointment()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = AppointmentDetailsState()
        )

    init {
        observeConnectivity()
    }
    
    fun onAction(action: AppointmentDetailsAction) {
        when(action){
            AppointmentDetailsAction.OnCancelClicked -> {
                cancelAppointment()
            }
            AppointmentDetailsAction.OnRescheduleClicked -> {}
            AppointmentDetailsAction.OnGoBackClicked -> {}
            AppointmentDetailsAction.ShowBottomSheet -> {
                _uiState.update { it.copy(showBottomSheet = !it.showBottomSheet) }
            }
        }
    }

    private fun getAppointment() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            appointmentService
                .getAppointmentById(appointmentId)
                .onSuccess { data ->
                    mainLogger.debug("Appointment ${data.data}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            appointment = data.data
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

    private fun cancelAppointment() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            appointmentService
                .cancelAppointment(appointmentId)
                .onSuccess { data ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            appointment = data.data
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