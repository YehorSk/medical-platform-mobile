package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_card.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.data.network.ConnectivityObserver
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.medical.domain.service.MedicalCardService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MedicalCardViewModel(
    private val mainLogger: MainLogger,
    private val connectivityObserver: ConnectivityObserver,
    private val medicalCardService: MedicalCardService
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _uiState = MutableStateFlow(MedicalCardState())
    val uiState = _uiState
        .onStart {
            if(!hasLoadedInitialData){
                getMedicalCard()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MedicalCardState()
        )

    init {
        observeConnectivity()
    }

    fun onAction(action: MedicalCardAction) {
        when (action) {
            is MedicalCardAction.GenderSelected -> {
                _uiState.update { state ->
                    state.copy(
                        form = state.form.copy(
                            gender = action.gender
                        )
                    )
                }
            }

            is MedicalCardAction.BloodTypeSelected -> {
                _uiState.update { state ->
                    state.copy(
                        form = state.form.copy(
                            bloodType = action.bloodType
                        )
                    )
                }
            }

            is MedicalCardAction.InsuranceProviderChanged -> {
                _uiState.update { state ->
                    state.copy(
                        form = state.form.copy(
                            insuranceCompany = action.value
                        )
                    )
                }
            }

            is MedicalCardAction.InsurancePolicyMemberIdChanged -> {
                _uiState.update { state ->
                    state.copy(
                        form = state.form.copy(
                            insuranceNumber = action.value
                        )
                    )
                }
            }

            is MedicalCardAction.BirthOfDateChanged -> {
                _uiState.update { state ->
                    state.copy(
                        form = state.form.copy(
                            dateOfBirth = action.date
                        )
                    )
                }
            }

            MedicalCardAction.HideSaveConfirmationDialog -> {
                _uiState.update {
                    it.copy(
                        showUpdateConfirmation = false
                    )
                }
            }
            MedicalCardAction.ShowSaveConfirmationDialog -> {
                _uiState.update {
                    it.copy(
                        showUpdateConfirmation = true
                    )
                }
            }

            MedicalCardAction.OnSaveClicked -> {
                saveMedicalCard()
            }
            MedicalCardAction.OnGoBackClicked -> {}
        }
    }

    private fun getMedicalCard() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            medicalCardService
                .getMyMedicalCard()
                .onSuccess { data ->
                    mainLogger.debug("MedicalCard ${data.data}")
                    val card = data.data
                    _uiState.update { state ->
                        state.copy(
                            medicalCard = card,
                            form = state.form.copy(
                                gender = card.gender,
                                bloodType = card.bloodType,
                                insuranceCompany = card.insuranceProvider,
                                insuranceNumber = card.insuranceNumber ?: "",
                                dateOfBirth = card.dateOfBirth
                            )
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

    private fun saveMedicalCard() {
        val form = _uiState.value.form
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            medicalCardService
                .updateMyMedicalCard(form)
                .onSuccess { data ->
                    mainLogger.debug("MedicalCard ${data.data}")
                    val card = data.data
                    _uiState.update { state ->
                        state.copy(
                            medicalCard = card,
                            form = state.form.copy(
                                gender = card.gender,
                                bloodType = card.bloodType,
                                insuranceCompany = card.insuranceProvider,
                                insuranceNumber = card.insuranceNumber ?: "",
                                dateOfBirth = card.dateOfBirth
                            )
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
}