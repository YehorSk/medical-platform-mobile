package com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class RegisterState(
    val registerForm: RegisterForm = RegisterForm(),
    val isEntryValid: Boolean = false,
    val passwordVisible: Boolean = false,
)


@Serializable
data class RegisterForm(
    @SerialName("first_name")
    val firstName: String = "",
    @SerialName("last_name")
    val lastName: String = "",
    val specialization: String = "",
    @SerialName("license_number")
    val licenseNumber: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    @SerialName("password_confirm")
    val passwordConfirm: String = "",
    val role: String = "Patient",
)