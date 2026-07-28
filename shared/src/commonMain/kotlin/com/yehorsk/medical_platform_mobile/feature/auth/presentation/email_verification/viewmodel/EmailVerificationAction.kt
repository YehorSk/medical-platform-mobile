package com.yehorsk.medical_platform_mobile.feature.auth.presentation.email_verification.viewmodel

sealed interface EmailVerificationAction {
    data object OnLoginClick: EmailVerificationAction
    data object OnCloseClick: EmailVerificationAction
}