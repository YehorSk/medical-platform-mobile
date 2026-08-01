package com.yehorsk.medical_platform_mobile.feature.auth.presentation.register_success.viewmodel

sealed interface RegisterSuccessEvent {
    data object ResendVerificationEmailSuccess: RegisterSuccessEvent
}