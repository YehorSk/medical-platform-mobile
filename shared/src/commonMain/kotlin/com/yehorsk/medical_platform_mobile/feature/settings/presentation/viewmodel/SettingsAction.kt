package com.yehorsk.medical_platform_mobile.feature.settings.presentation.viewmodel

sealed interface SettingsAction {

    data class UpdateFirstName(val value: String): SettingsAction

    data class UpdateSecondName(val value: String): SettingsAction

    data class UpdateTitle(val value: String): SettingsAction

    data class UpdatePhone(val value: String): SettingsAction

    data class UpdateAddress(val value: String): SettingsAction

    data class UpdateEmergencyContactName(val value: String): SettingsAction

    data class UpdateEmergencyContactPhone(val value: String): SettingsAction

    data object OnSaveDataClicked: SettingsAction

    data object OnGoBackClicked: SettingsAction

    data object GoToProfileScreen: SettingsAction

}