package com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterScreenViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = _uiState.asStateFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.ChangePwdVisibility -> {
                _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
            }

            RegisterAction.OnRegisterClicked -> {
                onRegisterClicked()
            }

            RegisterAction.OnSignInClicked -> {

            }

            is RegisterAction.UpdateEmail -> {
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm?.copy(email = action.email) ?: RegisterForm(
                            email = action.email
                        )
                    )
                }
            }

            is RegisterAction.UpdateFirstName -> {
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm?.copy(firstName = action.firstName)
                            ?: RegisterForm(firstName = action.firstName)
                    )
                }
            }

            is RegisterAction.UpdateLastName -> {
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm?.copy(lastName = action.lastName)
                            ?: RegisterForm(lastName = action.lastName)
                    )
                }
            }

            is RegisterAction.UpdateLicenseNumber -> {
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm?.copy(licenseNumber = action.licenseNumber)
                            ?: RegisterForm(licenseNumber = action.licenseNumber)
                    )
                }
            }

            is RegisterAction.UpdatePhone -> {
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm?.copy(phone = action.phone) ?: RegisterForm(
                            phone = action.phone
                        )
                    )
                }
            }

            is RegisterAction.UpdatePwd -> {
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm?.copy(password = action.pwd) ?: RegisterForm(
                            password = action.pwd
                        )
                    )
                }
            }

            is RegisterAction.UpdatePwdRepeat -> {
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm?.copy(passwordConfirm = action.pwdRepeat)
                            ?: RegisterForm(passwordConfirm = action.pwdRepeat)
                    )
                }
            }

            is RegisterAction.UpdateRole -> {
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm?.copy(role = action.role) ?: RegisterForm(
                            role = action.role
                        )
                    )
                }
            }

            is RegisterAction.UpdateSpecialization -> {
                _uiState.update {
                    it.copy(
                        registerForm = it.registerForm?.copy(specialization = action.specialization)
                            ?: RegisterForm(specialization = action.specialization)
                    )
                }
            }
        }
    }

    fun onRegisterClicked(){

    }

}