package com.yehorsk.medical_platform_mobile.feature.auth.presentation.local_auth.viewmodel

import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole

sealed interface LocalAuthAction {

    data class OnPinChanged(val value: String): LocalAuthAction

    data class SetUserData(val userId: String, val userRole: UserRole): LocalAuthAction

    data class OnConfirmPinChanged(val value: String): LocalAuthAction

    data object OnCheckPinClicked: LocalAuthAction

}