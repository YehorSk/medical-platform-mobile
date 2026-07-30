package com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.viewmodel

import com.yehorsk.medical_platform_mobile.core.domain.model.User

data class DashboardState(
    val user: User ?= null,
    val isLoading: Boolean = false
)

