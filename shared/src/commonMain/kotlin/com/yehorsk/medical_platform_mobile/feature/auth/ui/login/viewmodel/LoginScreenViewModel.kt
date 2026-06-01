package com.yehorsk.medical_platform_mobile.feature.auth.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginScreenViewModel: ViewModel() {

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