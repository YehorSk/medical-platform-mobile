package com.yehorsk.medical_platform_mobile.feature.auth.presentation.register_success.viewmodel

sealed interface RegisterSuccessAction {
    data class SetRegisteredEmail(val value: String): RegisterSuccessAction
    data object OnLoginClick: RegisterSuccessAction
    data object OnResendVerificationEmailClick: RegisterSuccessAction
}