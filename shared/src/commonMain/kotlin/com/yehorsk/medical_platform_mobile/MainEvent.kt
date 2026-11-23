package com.yehorsk.medical_platform_mobile

import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole

sealed interface MainEvent {
    data object OnSessionExpired: MainEvent
    data class OnAppLocked(val userId: String, val userRole: UserRole): MainEvent
}
