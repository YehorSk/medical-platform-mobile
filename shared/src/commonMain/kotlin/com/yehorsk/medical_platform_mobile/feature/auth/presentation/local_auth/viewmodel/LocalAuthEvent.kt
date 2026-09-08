package com.yehorsk.medical_platform_mobile.feature.auth.presentation.local_auth.viewmodel

sealed interface LocalAuthEvent {

    data object Success: LocalAuthEvent

}