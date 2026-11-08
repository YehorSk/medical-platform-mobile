package com.yehorsk.medical_platform_mobile.feature.auth.presentation.local_auth.viewmodel

sealed interface LocalAuthAction {

    data class OnPinChanged(val value: String): LocalAuthAction

    data class OnConfirmPinChanged(val value: String): LocalAuthAction

    data object OnCheckPinClicked: LocalAuthAction

}