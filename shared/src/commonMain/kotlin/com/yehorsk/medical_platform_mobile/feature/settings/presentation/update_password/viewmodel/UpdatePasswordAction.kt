package com.yehorsk.medical_platform_mobile.feature.settings.presentation.update_password.viewmodel

sealed interface UpdatePasswordAction {
    data class OnCurrentPasswordChanged(val value: String) : UpdatePasswordAction
    data class OnNewPasswordChanged(val value: String) : UpdatePasswordAction
    data class OnNewPasswordConfirmChanged(val value: String) : UpdatePasswordAction
    data object OnSubmit : UpdatePasswordAction
    data object OnGoBackClicked : UpdatePasswordAction
    data object ChangeCurrentPwdVisibility : UpdatePasswordAction
    data object ChangePwdVisibility : UpdatePasswordAction
    data object ChangePwdConfirmVisibility : UpdatePasswordAction
}