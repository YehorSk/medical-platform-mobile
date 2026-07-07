package com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.auth.domain.AuthService
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel.LoginEvent
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel.RegisterEvent
import com.yehorsk.medical_platform_mobile.util.getRole
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPasswordScreenViewModel(
    private val authService: AuthService,
): ViewModel() {

    private val eventChannel = Channel<ForgotPwdEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _uiState = MutableStateFlow(ForgotPasswordState())
    val uiState: StateFlow<ForgotPasswordState> = _uiState.asStateFlow()

    fun onAction(action: ForgotPasswordAction){
        when(action){
            is ForgotPasswordAction.UpdateEmail -> updateEmail(action.email)
            is ForgotPasswordAction.UpdateCode -> updateCode(action.code)
            is ForgotPasswordAction.UpdatePassword -> updatePwd(action.password)
            is ForgotPasswordAction.UpdatePasswordConfirm -> updatePwdConfirm(action.password)
            ForgotPasswordAction.OnSendResetTokenClicked -> { onSendResetTokenClicked() }
            ForgotPasswordAction.OnSendCodeClicked -> { onSendCodeClicked() }
            ForgotPasswordAction.OnSendNewPwdClicked -> { onSendNewPwdClicked() }
            ForgotPasswordAction.OnChangePwdVisibilityClicked -> { onChangePwdVisibilityClicked() }
        }
    }

    private fun onChangePwdVisibilityClicked() {
        _uiState.update { it.copy(isPwdVisible = !it.isPwdVisible) }
    }

    private fun onSendNewPwdClicked() {
        viewModelScope.launch {
            if(_uiState.value.isEntryValid){
                _uiState.update {
                    it.copy(
                        isLoading = true
                    )
                }
                authService
                    .resetPassword(
                        form = _uiState.value.form
                    )
                    .onSuccess { response ->
                        SnackbarController.sendEvent(
                            event = SnackbarEvent(
                                message = response.message
                            )
                        )
                        _uiState.update { it.copy(
                            isEntryValid = false
                        ) }
                        eventChannel.send(ForgotPwdEvent.Success)
                    }.onFailure { dataErrorRemote ->
                        SnackbarController.sendEvent(
                            event = SnackbarEvent(
                                error = dataErrorRemote
                            )
                        )
                        _uiState.update { it.copy(
                            isLoading = false
                        ) }
                    }
            }
        }
    }

    private fun onSendCodeClicked() {
        viewModelScope.launch {
            if(_uiState.value.isEntryValid){
                _uiState.update {
                    it.copy(
                        isLoading = true
                    )
                }
                authService
                    .verifyResetCode(
                        form = _uiState.value.form
                    )
                    .onSuccess { response ->
                        SnackbarController.sendEvent(
                            event = SnackbarEvent(
                                message = response.message
                            )
                        )
                        _uiState.update { it.copy(
                            currentStep = PasswordResetStep.Password,
                            isEntryValid = false,
                            isLoading = false
                        ) }
                    }.onFailure { dataErrorRemote ->
                        SnackbarController.sendEvent(
                            event = SnackbarEvent(
                                error = dataErrorRemote
                            )
                        )
                        _uiState.update { it.copy(
                            isLoading = false
                        ) }
                    }
            }
        }
    }

    private fun onSendResetTokenClicked() {
        viewModelScope.launch {
            if(_uiState.value.isEntryValid){
                _uiState.update {
                    it.copy(
                        isLoading = true
                    )
                }
                authService
                    .forgotPassword(
                        form = _uiState.value.form
                    )
                    .onSuccess { response ->
                        SnackbarController.sendEvent(
                            event = SnackbarEvent(
                                message = response.message
                            )
                        )
                        _uiState.update { it.copy(
                            currentStep = PasswordResetStep.Code,
                            isEntryValid = false,
                            isLoading = false
                        ) }
                    }.onFailure { dataErrorRemote ->
                        SnackbarController.sendEvent(
                            event = SnackbarEvent(
                                error = dataErrorRemote
                            )
                        )
                        _uiState.update { it.copy(
                            isLoading = false
                        ) }
                    }
            }
        }
    }

    private fun updatePwdConfirm(pwd: String) {
        _uiState.update { it.copy(form = it.form.copy(passwordConfirm = pwd)) }
        validateForm()
    }

    private fun updatePwd(pwd: String) {
        _uiState.update { it.copy(form = it.form.copy(password = pwd)) }
        validateForm()
    }

    private fun updateCode(code: String) {
        _uiState.update { it.copy(form = it.form.copy(code = code)) }
        validateForm()
    }

    private fun updateEmail(email: String) {
        _uiState.update { it.copy(form = it.form.copy(email = email)) }
        validateForm()
    }

    private fun validateForm(){
        with(_uiState.value){
            when(currentStep){
                PasswordResetStep.Email -> {
                    _uiState.update { it.copy(
                        isEntryValid = form.email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
                    ) }
                }
                PasswordResetStep.Code -> {
                    val isValid = form.code.length == 6 && form.code.all { it.isDigit() }
                    _uiState.update { it.copy(
                        isEntryValid = isValid
                    ) }
                }
                PasswordResetStep.Password -> {
                    val isValid = form.password.isNotEmpty() && (form.password == form.passwordConfirm)
                    _uiState.update { it.copy(
                        isEntryValid = isValid
                    ) }
                }
            }
        }
    }

}