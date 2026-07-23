package com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.data.network.ConnectivityObserver
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.domain.service.DoctorService
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.request.UserIdRequest
import com.yehorsk.medical_platform_mobile.feature.connections.domain.service.PatientHasDoctorService
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
class DoctorDetailsViewModel(
    private val doctorService: DoctorService,
    private val patientHasDoctorService: PatientHasDoctorService,
    private val mainLogger: MainLogger,
    private val connectivityObserver: ConnectivityObserver
): ViewModel() {

    init {
        observeConnectivity()
    }

    private val _uiState = MutableStateFlow(DoctorDetailsState())
    val uiState = _uiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = DoctorDetailsState()
        )

    fun onAction(action: DoctorDetailsAction){
        when(action){
            is DoctorDetailsAction.OnGetDoctorById -> {
                getDoctor(action.id)
            }
            DoctorDetailsAction.OnGrantAccessClicked -> { approveAccess() }
            DoctorDetailsAction.GoBackClicked -> {}
            DoctorDetailsAction.OnApproveAccessClicked -> {  }
            DoctorDetailsAction.OnBookAppointmentClicked -> {}
            DoctorDetailsAction.OnDeclineAccessClicked -> {}
            DoctorDetailsAction.OnOpenChatClicked -> {}
            DoctorDetailsAction.OnRevokeAccessClicked -> {}
        }
    }

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

    private fun approveAccess(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingDoctor = true) }
            _uiState.value.doctorDetails?.user.let { user ->
                patientHasDoctorService
                    .patientGiveAccessToDoctor(UserIdRequest(user!!.id))
                    .onSuccess { response ->
                        _uiState.update {
                            it.copy(
                                isLoadingDoctor = false,
                                patientAccess = response.data
                            )
                        }
                    }
                    .onFailure { dataErrorRemote ->
                        _uiState.update { it.copy(isLoadingDoctor = false) }
                        SnackbarController.sendEvent(SnackbarEvent(error = dataErrorRemote))
                    }
            }
        }
    }

    private fun getDoctor(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingDoctor = true) }
            doctorService
                .getDoctorById(id)
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            isLoadingDoctor = false,
                            doctorDetails = response.data.doctor,
                            patientAccess = response.data.access
                        )
                    }
                }
                .onFailure { dataErrorRemote ->
                    _uiState.update { it.copy(isLoadingDoctor = false) }
                    SnackbarController.sendEvent(SnackbarEvent(error = dataErrorRemote))
                }
        }
    }

}