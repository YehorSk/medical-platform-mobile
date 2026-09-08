package com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel

import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole

sealed interface LoginEvent {
    data class Success(
        val userId: String,
        val role: UserRole
    ): LoginEvent
}