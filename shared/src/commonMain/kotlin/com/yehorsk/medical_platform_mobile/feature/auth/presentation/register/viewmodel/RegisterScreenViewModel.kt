package com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.yehorsk.medical_platform_mobile.core.data.mappers.toRegisterFormErrors
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.auth.domain.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterScreenViewModel(
    private val authService: AuthService
): ViewModel() {

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

            is RegisterAction.UpdateEmail -> {
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm.copy(email = action.email)
                    )
                }
            }

            is RegisterAction.UpdateFirstName -> {
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm.copy(firstName = action.firstName)
                    )
                }
            }

            is RegisterAction.UpdateLastName -> {
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm.copy(lastName = action.lastName)
                    )
                }
            }

            is RegisterAction.UpdateLicenseNumber -> {
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm.copy(licenseNumber = action.licenseNumber)
                    )
                }
            }

            is RegisterAction.UpdatePhone -> {
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm.copy(phone = action.phone)
                    )
                }
            }

            is RegisterAction.UpdatePwd -> {
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm.copy(password = action.pwd)
                    )
                }
            }

            is RegisterAction.UpdatePwdRepeat -> {
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm.copy(passwordConfirm = action.pwdRepeat)
                    )
                }
            }

            is RegisterAction.UpdateRole -> {
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm.copy(role = action.role)
                    )
                }
            }

            is RegisterAction.UpdateSpecialization -> {
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm.copy(specialization = action.specialization)
                    )
                }
            }
        }
    }

    fun register(){
        viewModelScope.launch {
            clearFormErrors()
            authService
                .register(
                    form = _uiState.value.registerForm
                )
                .onSuccess { response, _ ->
                    Logger.withTag("RegisterScreenViewModel").i { response.message }
                }.onFailure { dataErrorRemote ->
                    when(dataErrorRemote) {
                        is DataError.Remote.ValidationError -> {
                            dataErrorRemote.errors?.let { errors ->
                                Logger.withTag("RegisterScreenViewModel").e { errors.toRegisterFormErrors().toString() }
                                _uiState.update { it.copy(
                                    registerFormErrors = errors.toRegisterFormErrors()
                                ) }
                            }
                        }
                        else -> Logger.withTag("RegisterScreenViewModel").e { "Error $dataErrorRemote"}
                    }
                }
        }
    }

    fun clearFormErrors(){
        _uiState.update { it.copy(
            registerFormErrors = RegisterFormErrors()
        ) }
    }

}