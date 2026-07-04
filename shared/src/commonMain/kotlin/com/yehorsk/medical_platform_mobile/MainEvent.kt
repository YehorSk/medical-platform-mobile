package com.yehorsk.medical_platform_mobile

sealed interface MainEvent {
    data object OnSessionExpired: MainEvent
}