package com.yehorsk.medical_platform_mobile

import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole

data class MainState(
    val isLoggedIn: Boolean = false,
    val isCheckingAuth: Boolean = false,
    val isLoading: Boolean = true,
    val userRole: UserRole? = null
)