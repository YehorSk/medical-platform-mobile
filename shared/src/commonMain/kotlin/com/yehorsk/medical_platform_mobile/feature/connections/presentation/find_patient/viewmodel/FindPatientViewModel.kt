package com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_patient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.data.network.ConnectivityObserver
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.connections.domain.service.PatientHasDoctorService
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.viewmodel.FindDoctorState
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
class FindPatientViewModel(
    private val patientHasDoctorService: PatientHasDoctorService,
    private val mainLogger: MainLogger,
    private val connectivityObserver: ConnectivityObserver
): ViewModel() {

    private var hasLoadedInitialData = false

    init {
        observeConnectivity()
    }

    private val _uiState = MutableStateFlow(FindPatientState())
    val uiState = _uiState
        .onStart {
            if(!hasLoadedInitialData){
                getAllPatients()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = FindPatientState()
        )

    private fun observeConnectivity() {
        connectivityObserver.isConnected
            .debounce(1.seconds)
            .distinctUntilChanged()
            .drop(1)
            .onEach { connected ->
                mainLogger.debug("Connectivity = $connected")
                _uiState.update { it.copy(isConnected = connected) }
                if(connected) {
                    mainLogger.debug("Get Doctors: wifi")
                    getAllPatients()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getAllPatients(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            patientHasDoctorService
                .getMyPatients()
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            patients = response.data,
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