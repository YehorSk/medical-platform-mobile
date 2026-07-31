package com.yehorsk.medical_platform_mobile.feature.settings.presentation.viewmodel

sealed interface SettingsScreenEvent {

    data object OnLogoutSuccess: SettingsScreenEvent

}