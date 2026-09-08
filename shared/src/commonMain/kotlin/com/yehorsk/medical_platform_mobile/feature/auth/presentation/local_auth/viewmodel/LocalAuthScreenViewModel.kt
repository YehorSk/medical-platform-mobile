package com.yehorsk.medical_platform_mobile.feature.auth.presentation.local_auth.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.domain.repository.PinStorage
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel.LoginEvent
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel.LoginState
import com.yehorsk.medical_platform_mobile.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.error_pin_incorrect
import medicalplatformmobile.shared.generated.resources.error_pin_invalid
import medicalplatformmobile.shared.generated.resources.error_pin_mismatch
import medicalplatformmobile.shared.generated.resources.error_pin_required

class LocalAuthScreenViewModel(
    private val pinStorage: PinStorage,
    private val mainLogger: MainLogger
): ViewModel() {

    private val eventChannel = Channel<LocalAuthEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _uiState = MutableStateFlow(LocalAuthState())
    val uiState: StateFlow<LocalAuthState> = _uiState
        .onStart {
            checkPinExists()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = LocalAuthState()
        )

    fun onAction(action: LocalAuthAction){
        when(action){
            is LocalAuthAction.OnPinChanged -> onPinChanged(action.value)
            is LocalAuthAction.OnConfirmPinChanged -> onConfirmPinChanged(action.value)
            is LocalAuthAction.SetUserData -> onSetUserData(action.userId, action.userRole)
            LocalAuthAction.OnCheckPinClicked -> onCheckClicked()
        }
    }

    private fun onFormValidate(){
        _uiState.update {
            it.copy(
                isFormValid = when(_uiState.value.localAuthType){
                    LocalAuthType.CREATE_PIN -> isPinValid(_uiState.value.pinCode)
                    LocalAuthType.CONFIRM_PIN ->
                        isPinValid(_uiState.value.pinCodeConfirm) &&
                                _uiState.value.pinCode == _uiState.value.pinCodeConfirm
                    LocalAuthType.ENTER_PIN -> isPinValid(_uiState.value.pinCode)
                }
            )
        }
    }

    private fun onSetUserData(
        userId: String,
        userRole: UserRole
    ) {
        _uiState.update {
            it.copy(
                userId = userId,
                userRole = userRole
            )
        }
    }

    private fun onConfirmPinChanged(value: String) {
        _uiState.update {
            it.copy(
                pinCodeConfirm = value,
                error = null
            )
        }
        onFormValidate()
    }

    private fun onPinChanged(value: String) {
        _uiState.update {
            it.copy(
                pinCode = value,
                error = null
            )
        }
        onFormValidate()
    }

    private fun checkPinExists() {
        _uiState.value.userId?.let { id ->
            _uiState.update {
                it.copy(isLoading = true)
            }

            viewModelScope.launch {
                val hasPin = pinStorage.hasPin(id)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        localAuthType = if (hasPin) {
                            LocalAuthType.ENTER_PIN
                        } else {
                            LocalAuthType.CREATE_PIN
                        }
                    )
                }
            }
        }
    }

    private fun onCheckClicked() {
        _uiState.value.userId?.let { id ->
            viewModelScope.launch {
                when(_uiState.value.localAuthType){
                    LocalAuthType.CREATE_PIN -> {
                        if (!isPinValid(_uiState.value.pinCode)) {
                            _uiState.update {
                                it.copy(
                                    error = UiText.Resource(UiRes.string.error_pin_invalid)
                                )
                            }
                            return@launch
                        }

                        _uiState.update {
                            it.copy(
                                localAuthType = LocalAuthType.CONFIRM_PIN,
                                pinCodeConfirm = "",
                                error = null
                            )
                        }
                    }
                    LocalAuthType.CONFIRM_PIN -> {
                        if (_uiState.value.pinCode != _uiState.value.pinCodeConfirm) {
                            _uiState.update {
                                it.copy(
                                    error = UiText.Resource(UiRes.string.error_pin_mismatch)
                                )
                            }
                            return@launch
                        }
                        pinStorage.setPin(id, _uiState.value.pinCode)
                        eventChannel.send(LocalAuthEvent.Success)
                    }
                    LocalAuthType.ENTER_PIN -> {
                        if (!isPinValid(_uiState.value.pinCode)) {
                            _uiState.update {
                                it.copy(
                                    error = UiText.Resource(UiRes.string.error_pin_required)
                                )
                            }
                            return@launch
                        }

                        val isCorrect = pinStorage.verifyPin(
                            id,
                            _uiState.value.pinCode
                        )

                        if (isCorrect) {
                            eventChannel.send(
                                LocalAuthEvent.Success
                            )
                        }else {
                            _uiState.update {
                                it.copy(
                                    error = UiText.Resource(UiRes.string.error_pin_incorrect)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun isPinValid(pin: String): Boolean {
        return pin.length in 4..6 &&
                pin.all(Char::isDigit)
    }

}