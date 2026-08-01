package com.yehorsk.medical_platform_mobile.feature.auth.presentation.register_success.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.core.util.toUiText
import com.yehorsk.medical_platform_mobile.feature.auth.domain.AuthService
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterSuccessScreenViewModel(
    private val authService: AuthService,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val eventChannel = Channel<RegisterSuccessEvent>()
    val events = eventChannel.receiveAsFlow()

    private val email = savedStateHandle.get<String>("email")
        ?: throw IllegalStateException("No email passed")

    private val _uiState = MutableStateFlow(RegisterSuccessState(
        registeredEmail = email
    ))
    val uiState = _uiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = RegisterSuccessState()
        )


    fun onAction(action: RegisterSuccessAction) {
        when (action) {
            is RegisterSuccessAction.OnResendVerificationEmailClick -> resendEmailVerification()
            is RegisterSuccessAction.SetRegisteredEmail -> {
                _uiState.update { it.copy(
                    registeredEmail = action.value
                ) }
            }
            RegisterSuccessAction.OnLoginClick -> {}
        }
    }

    private fun resendEmailVerification() {
        if(uiState.value.isLoading){
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(
                isLoading = true
            ) }
            authService
                .resendEmailVerification(email)
                .onSuccess { response ->
                    _uiState.update { it.copy(
                        isLoading = false
                    ) }
                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            message = response.message
                        )
                    )
                    eventChannel.send(RegisterSuccessEvent.ResendVerificationEmailSuccess)
                }
                .onFailure { error ->
                    _uiState.update { it.copy(
                        isLoading = false,
                        resendVerificationError = error.toUiText()
                    ) }
                }
        }
    }

}