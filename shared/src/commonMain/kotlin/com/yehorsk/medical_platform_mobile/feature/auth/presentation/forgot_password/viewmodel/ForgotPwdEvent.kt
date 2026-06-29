package com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password.viewmodel

sealed interface ForgotPwdEvent {
    data object Success: ForgotPwdEvent
}