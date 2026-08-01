package com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.yehorsk.medical_platform_mobile.core.domain.repository.SessionStorage
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.core.util.toUiText
import com.yehorsk.medical_platform_mobile.feature.auth.data.mappers.toAuthDataDto
import com.yehorsk.medical_platform_mobile.feature.auth.domain.AuthService
import com.yehorsk.medical_platform_mobile.util.UiText
import com.yehorsk.medical_platform_mobile.util.getRole
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.error_email_not_verified
import medicalplatformmobile.shared.generated.resources.error_invalid_credentials

class LoginScreenViewModel(
    private val authService: AuthService,
    private val sessionStorage: SessionStorage
): ViewModel() {

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState
        .onStart {
            validateForm()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = LoginState()
        )

    fun onAction(action: LoginAction){
        when(action){
            LoginAction.ChangePwdVisibility -> changePwdVisibility()
            is LoginAction.UpdateEmail -> updateEmail(action.email)
            is LoginAction.UpdatePwd -> updatePwd(action.pwd)
            LoginAction.OnSignInClicked -> signIn()
            LoginAction.OnSignUpClicked -> {}
            LoginAction.OnForgotPwdClicked -> {}
        }
    }

    private fun validateForm(){
        with(_uiState.value){
            _uiState.update { it.copy(
                isEntryValid = loginForm.password.isNotBlank() && loginForm.email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
            ) }
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            if(_uiState.value.isEntryValid){
                _uiState.update { it.copy(
                    isLoading = true
                ) }
                authService
                    .login(
                        form = _uiState.value.loginForm
                    )
                    .onSuccess { response ->
                        sessionStorage.setAuthData(authData = response.data.toAuthDataDto())
                        _uiState.update { it.copy(
                            isLoading = false
                        ) }
                        eventChannel.send(LoginEvent.Success(getRole(response.data.user.role)))
                        SnackbarController.sendEvent(
                            event = SnackbarEvent(
                                message = response.message
                            )
                        )
                    }.onFailure { dataErrorRemote ->
                        val errorMessage = when(dataErrorRemote) {
                            DataError.Remote.Status.UNAUTHORIZED -> UiText.Resource(UiRes.string.error_invalid_credentials)
                            DataError.Remote.Status.FORBIDDEN -> UiText.Resource(UiRes.string.error_email_not_verified)
                            else -> dataErrorRemote.toUiText()
                        }
                        SnackbarController.sendEvent(
                            event = SnackbarEvent(
                                message = errorMessage.asStringAsync()
                            )
                        )
                        _uiState.update { it.copy(
                            isLoading = false
                        ) }
                    }
            }
        }
    }

    private fun updatePwd(pwd: String) {
        validateForm()
        _uiState.update { it.copy(loginForm = it.loginForm.copy(password = pwd)) }
    }

    private fun updateEmail(email: String) {
        validateForm()
        _uiState.update { it.copy(loginForm = it.loginForm.copy(email = email)) }
    }

    private fun changePwdVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

}