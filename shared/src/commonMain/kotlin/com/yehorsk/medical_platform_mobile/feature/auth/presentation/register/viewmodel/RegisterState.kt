package com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel

import kotlinx.serialization.Serializable

data class RegisterState(
    val registerForm: RegisterForm = RegisterForm(),
    val isEntryValid: Boolean = false,
    val passwordVisible: Boolean = false,
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
    val firstName: String = "",
    val lastName: String = "",
    val specialization: String = "",
    val licenseNumber: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val passwordConfirm: String = "",
    val role: String = "Patient",
)