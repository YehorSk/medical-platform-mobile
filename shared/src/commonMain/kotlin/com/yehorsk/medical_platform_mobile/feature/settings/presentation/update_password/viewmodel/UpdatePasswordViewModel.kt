package com.yehorsk.medical_platform_mobile.feature.settings.presentation.update_password.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.settings.domain.SettingsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UpdatePasswordViewModel(
    private val settingsService: SettingsService
): ViewModel() {

    private val _uiState = MutableStateFlow(UpdatePasswordState())
    val uiState = _uiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = UpdatePasswordState()
        )

    fun onAction(action: UpdatePasswordAction) {
        when (action) {
            is UpdatePasswordAction.OnCurrentPasswordChanged -> {
                _uiState.update {
                    it.copy(form = it.form.copy(currentPassword = action.value))
                }
                validate()
            }

            is UpdatePasswordAction.OnNewPasswordChanged -> {
                _uiState.update {
                    it.copy(form = it.form.copy(password = action.value))
                }
                validate()
            }

            is UpdatePasswordAction.OnNewPasswordConfirmChanged -> {
                _uiState.update {
                    it.copy(form = it.form.copy(passwordConfirm = action.value))
                }
                validate()
            }
            UpdatePasswordAction.OnSubmit -> {
                updatePassword()
            }

            UpdatePasswordAction.OnGoBackClicked -> {

            }
        }
    }

    private fun updatePassword() {
        viewModelScope.launch {
            _uiState.update { it.copy(
                isLoading = true
            ) }
            settingsService
                .updatePassword(_uiState.value.form)
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            form = UpdatePwdForm(),
                            isValid = false,
                            isLoading = false
                        )
                    }
                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            message = response.message
                        )
                    )
                }.onFailure { dataErrorRemote ->
                    _uiState.update { it.copy(
                        isLoading = false
                    ) }
                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            error = dataErrorRemote
                        )
                    )
                }
        }
    }

    private fun validate() {
        val form = _uiState.value.form
        val isValid = form.currentPassword.isNotBlank() &&
                form.password.length >= 8 &&
                form.password == form.passwordConfirm &&
                form.password != form.currentPassword

        _uiState.update { it.copy(isValid = isValid) }
    }

}