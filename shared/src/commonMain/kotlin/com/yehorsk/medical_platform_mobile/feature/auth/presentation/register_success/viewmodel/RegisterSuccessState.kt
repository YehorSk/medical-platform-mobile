package com.yehorsk.medical_platform_mobile.feature.auth.presentation.register_success.viewmodel

import com.yehorsk.medical_platform_mobile.util.UiText

data class RegisterSuccessState(
    val registeredEmail: String = "",
    val isLoading: Boolean = false,
    val resendVerificationError: UiText? = null
)