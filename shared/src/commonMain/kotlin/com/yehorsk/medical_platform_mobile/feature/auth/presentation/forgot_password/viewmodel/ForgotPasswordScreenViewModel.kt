package com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password.viewmodel

import androidx.lifecycle.ViewModel
import com.yehorsk.medical_platform_mobile.feature.auth.domain.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ForgotPasswordScreenViewModel(
    private val authService: AuthService,
): ViewModel() {

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

    }

    private fun onSendCodeClicked() {
        _uiState.update {
            it.copy(
                currentStep = PasswordResetStep.Password,
                isEntryValid = false
            )
        }
    }

    private fun onSendResetTokenClicked() {
        _uiState.update {
            it.copy(
                currentStep = PasswordResetStep.Code,
                isEntryValid = false
            )
        }
    }

    private fun updatePwdConfirm(pwd: String) {
        _uiState.update { it.copy(form = it.form.copy(newPasswordConfirm = pwd)) }
        validateForm()
    }

    private fun updatePwd(pwd: String) {
        _uiState.update { it.copy(form = it.form.copy(newPassword = pwd)) }
        validateForm()
    }

    private fun updateCode(code: String) {
        _uiState.update { it.copy(form = it.form.copy(otpCode = code)) }
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
                    val isValid = form.otpCode.length == 6 && form.otpCode.all { it.isDigit() }
                    _uiState.update { it.copy(
                        isEntryValid = isValid
                    ) }
                }
                PasswordResetStep.Password -> {
                    val isValid = form.newPassword.isNotEmpty() && (form.newPassword == form.newPasswordConfirm)
                    _uiState.update { it.copy(
                        isEntryValid = isValid
                    ) }
                }
            }
        }
    }

}