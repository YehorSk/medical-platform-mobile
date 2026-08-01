package com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.yehorsk.medical_platform_mobile.core.data.mappers.toRegisterFormErrors
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.auth.domain.AuthService
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel.LoginEvent
import com.yehorsk.medical_platform_mobile.util.getRole
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterScreenViewModel(
    private val authService: AuthService
): ViewModel() {

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _uiState = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = _uiState.asStateFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.ChangePwdVisibility -> {
                _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
            }

            RegisterAction.OnRegisterClicked -> {
                register()
            }

            RegisterAction.OnSignInClicked -> {

            }

            RegisterAction.OnValidateForm -> {
                validateForm()
            }

            is RegisterAction.UpdateEmail -> {
                validateForm()
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm.copy(email = action.email)
                    )
                }
            }

            is RegisterAction.UpdateFirstName -> {
                validateForm()
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm.copy(firstName = action.firstName)
                    )
                }
            }

            is RegisterAction.UpdateLastName -> {
                validateForm()
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm.copy(lastName = action.lastName)
                    )
                }
            }

            is RegisterAction.UpdateLicenseNumber -> {
                validateForm()
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm.copy(licenseNumber = action.licenseNumber)
                    )
                }
            }

            is RegisterAction.UpdatePhone -> {
                validateForm()
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm.copy(phone = action.phone)
                    )
                }
            }

            is RegisterAction.UpdatePwd -> {
                validateForm()
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm.copy(password = action.pwd)
                    )
                }
            }

            is RegisterAction.UpdatePwdRepeat -> {
                validateForm()
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm.copy(passwordConfirm = action.pwdRepeat)
                    )
                }
            }

            is RegisterAction.UpdateRole -> {
                validateForm()
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm.copy(role = action.role)
                    )
                }
            }
        }
    }

    private fun validateForm(){
        with(_uiState.value.registerForm) {
            val isLicenseValid = if(getRole(role) == UserRole.DOCTOR){
                licenseNumber.isNotBlank()
            } else true
            val isPwdValid = password.isNotBlank() && password == passwordConfirm
            val isCredValid = firstName.isNotBlank() && lastName.isNotBlank()
            val isPhoneValid = phone.isNotBlank() && phone.matches(Regex("^\\+?[0-9]{7,15}$"))
            val isEmailValid = email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
            _uiState.update {
                it.copy(
                    isEntryValid = isLicenseValid && isPwdValid && isCredValid && isEmailValid && isPhoneValid
                )
            }
        }
    }

    private fun register(){
        viewModelScope.launch {
            if(_uiState.value.isEntryValid){
                _uiState.update { it.copy(
                    isLoading = true
                ) }
                clearFormErrors()
                authService
                    .register(
                        form = _uiState.value.registerForm
                    )
                    .onSuccess { response ->
                        _uiState.update { it.copy(
                            isLoading = false
                        ) }
                        eventChannel.send(RegisterEvent.Success(_uiState.value.registerForm.email))
                        SnackbarController.sendEvent(
                            event = SnackbarEvent(
                                message = response.message
                            )
                        )
                        Logger.withTag("RegisterScreenViewModel").i { response.message }
                    }.onFailure { dataErrorRemote ->
                        when(dataErrorRemote) {
                            is DataError.Remote.ValidationError -> {
                                dataErrorRemote.errors?.let { errors ->
                                    _uiState.update { it.copy(
                                        registerFormErrors = errors.toRegisterFormErrors()
                                    ) }
                                }
                            }
                            else -> {
                                SnackbarController.sendEvent(
                                    event = SnackbarEvent(
                                        error = dataErrorRemote
                                    )
                                )
                            }
                        }
                        _uiState.update { it.copy(
                            isLoading = false
                        ) }
                    }
            }
        }
    }

    private fun clearFormErrors(){
        _uiState.update { it.copy(
            registerFormErrors = RegisterFormErrors()
        ) }
    }

}