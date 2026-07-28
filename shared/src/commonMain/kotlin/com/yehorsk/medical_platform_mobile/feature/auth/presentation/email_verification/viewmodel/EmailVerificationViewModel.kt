package com.yehorsk.medical_platform_mobile.feature.auth.presentation.email_verification.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.auth.domain.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EmailVerificationViewModel(
    private val authService: AuthService,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val token = savedStateHandle.get<String>("token")

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(EmailVerificationState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                verifyUsersEmail()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = EmailVerificationState()
        )

    private fun verifyUsersEmail(){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isVerifying = true
                )
            }
            authService
                .verifyUsersEmail(token ?: "Invalid token")
                .onSuccess {
                    _state.update { it.copy(
                        isVerifying = false,
                        isVerified = true
                    ) }
                }
                .onFailure {
                    _state.update { it.copy(
                        isVerifying = false,
                        isVerified = false
                    ) }
                }
        }
    }

}