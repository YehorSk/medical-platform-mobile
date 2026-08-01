package com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel

sealed interface RegisterEvent {
    data class Success(val email: String): RegisterEvent
}