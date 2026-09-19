package com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record.viewmodel

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
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyHitRegion
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyRegion
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.MedicalRecordType
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

class CreateMedicalRecordViewModel(
    private val mainLogger: MainLogger,
    private val connectivityObserver: ConnectivityObserver,
    private val appointmentService: AppointmentService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val appointmentId = savedStateHandle.get<String>("appointmentId")
        ?: throw IllegalStateException("Appointment id is missing")

    private val _uiState = MutableStateFlow(CreateRecordScreenState())
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
            initialValue = CreateRecordScreenState()
        )

    init {
        observeConnectivity()
    }

    fun onAction(action: CreateRecordAction) {
        when (action) {
            is CreateRecordAction.OnBodyPartSelected -> onBodyPartSelected(action.part)
            is CreateRecordAction.OnBodyRegionSelected -> onBodyRegionSelected(action.region)
            is CreateRecordAction.OnTabSelected -> onTabSelected(action.tab)
            is CreateRecordAction.OnMedRecordTypeSelected -> onMedRecordSelected(action.type)
            CreateRecordAction.OnGoBackClicked -> {}
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

    private fun observeConnectivity() {
        connectivityObserver.isConnected
            .distinctUntilChanged()
            .onEach { connected ->
                mainLogger.debug("Connectivity = $connected")
                _uiState.update { it.copy(isConnected = connected) }
            }
            .launchIn(viewModelScope)
    }

    private fun onBodyPartSelected(part: BodyHitRegion) {
        _uiState.update { state ->
            val selectedParts = state.form.selectedBodyParts

            state.copy(
                form = state.form.copy(
                    selectedBodyParts = if (part in selectedParts) {
                        selectedParts - part
                    } else {
                        selectedParts + part
                    }
                )
            )
        }
    }

    private fun onBodyRegionSelected(region: BodyRegion) {
        _uiState.update { state ->
            state.copy(
                selectedRegion = region
            )
        }
    }

    private fun onMedRecordSelected(type: MedicalRecordType) {
        _uiState.update { state ->
            state.copy(
                form = state.form.copy(
                    medicalRecordType =  type
                )
            )
        }
    }

    private fun onTabSelected(tab: CreateRecordTab) {
        _uiState.update { state ->
            state.copy(
                currentTab = tab
            )
        }
    }
}