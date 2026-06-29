package com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password.viewmodel

import kotlinx.serialization.Serializable

data class ForgotPasswordState(
    val isLoading: Boolean = false,
    val isEntryValid: Boolean = false,
    val error: String = "",
    val isPwdVisible: Boolean = false,
    val form: ForgotPasswordForm = ForgotPasswordForm(),
    val currentStep: PasswordResetStep = PasswordResetStep.Email
)

@Serializable
data class ForgotPasswordForm(
    val email: String = "",
    val code: String = "",
    val password: String = "",
    val passwordConfirm: String = "",
)

enum class PasswordResetStep { Email, Code, Password }