package com.yehorsk.medical_platform_mobile.feature.settings.presentation.account_activity.viewmodel

import com.yehorsk.medical_platform_mobile.feature.settings.domain.model.AuthEvent

data class AccountActivityState(
    val events: List<AuthEvent> = emptyList(),
    val isLoading: Boolean = false,
    val isConnected: Boolean = true
)
