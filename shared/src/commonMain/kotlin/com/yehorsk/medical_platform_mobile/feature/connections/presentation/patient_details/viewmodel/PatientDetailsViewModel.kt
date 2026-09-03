package com.yehorsk.medical_platform_mobile.feature.connections.presentation.patient_details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.data.network.ConnectivityObserver
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.connections.domain.service.MedicalCardService
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.viewmodel.DoctorDetailsState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@OptIn(FlowPreview::class)
class PatientDetailsViewModel(
    private val medicalCardService: MedicalCardService,
    private val mainLogger: MainLogger,
    private val connectivityObserver: ConnectivityObserver
): ViewModel() {

    init {
        observeConnectivity()
    }

    private val _uiState = MutableStateFlow(PatientDetailsState())
    val uiState = _uiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = PatientDetailsState()
        )

    private fun observeConnectivity() {
        connectivityObserver.isConnected
            .debounce(1.seconds)
            .distinctUntilChanged()
            .drop(1)
            .onEach { connected ->
                mainLogger.debug("Connectivity = $connected")
                _uiState.update { it.copy(isConnected = connected) }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: PatientDetailsAction){
        when(action){
            is PatientDetailsAction.OnGetPatientById -> {
                getPatient(action.id)
            }
            PatientDetailsAction.GoBackClicked -> {}
        }
    }

    private fun getPatient(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            medicalCardService
                .getPatientById(id)
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            patient = response.data
                        )
                    }
                }
                .onFailure { dataErrorRemote ->
                    _uiState.update { it.copy(isLoading = false) }
                    SnackbarController.sendEvent(SnackbarEvent(error = dataErrorRemote))
                }
        }
    }

}