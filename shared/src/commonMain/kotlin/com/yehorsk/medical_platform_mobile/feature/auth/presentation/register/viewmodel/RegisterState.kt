package com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel

import kotlinx.serialization.Serializable

data class RegisterState(
    val registerForm: RegisterForm = RegisterForm(),
    val isEntryValid: Boolean = false,
    val passwordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val registerFormErrors: RegisterFormErrors = RegisterFormErrors()
)

data class RegisterFormErrors(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val role: String = "",
    val licenseNumber: String = "",
    val password: String = "",
    val passwordConfirm: String = "",
    val message: String = ""
)


@Serializable
data class RegisterForm(
    val firstName: String = "test",
    val lastName: String = "test",
    val licenseNumber: String = "12312321323",
    val email: String = "test@gmail.com",
    val phone: String = "12312312312132",
    val password: String = "12345678",
    val passwordConfirm: String = "12345678",
    val role: String = "Patient",
)