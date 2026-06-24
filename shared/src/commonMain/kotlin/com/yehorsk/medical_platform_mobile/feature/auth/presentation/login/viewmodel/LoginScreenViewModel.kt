package com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.auth.domain.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val authService: AuthService
): ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    fun onAction(action: LoginAction){
        when(action){
            LoginAction.ChangePwdVisibility -> changePwdVisibility()
            is LoginAction.UpdateEmail -> updateEmail(action.email)
            is LoginAction.UpdatePwd -> updatePwd(action.pwd)
            LoginAction.OnSignInClicked -> signIn()
            LoginAction.OnSignUpClicked -> {}
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            authService
                .login(
                    form = _uiState.value.loginForm
                )
                .onSuccess { data, message ->
                    Logger.withTag("LoginScreenViewModel").i { message ?: "Test" }
                }.onFailure { dataErrorRemote ->
                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            error = dataErrorRemote
                        )
                    )
                }
        }
    }

    private fun updatePwd(pwd: String) {
        _uiState.update { it.copy(loginForm = it.loginForm.copy(password = pwd)) }
    }

    private fun updateEmail(email: String) {
        _uiState.update { it.copy(loginForm = it.loginForm.copy(email = email)) }
    }

    private fun changePwdVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

}