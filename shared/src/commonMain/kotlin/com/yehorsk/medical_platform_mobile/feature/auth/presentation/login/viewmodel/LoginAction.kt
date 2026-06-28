package com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel

sealed interface LoginAction {

    data class UpdateEmail(val email: String): LoginAction
    data class UpdatePwd(val pwd: String): LoginAction
    data object ChangePwdVisibility: LoginAction
    data object OnSignUpClicked: LoginAction
    data object OnSignInClicked: LoginAction
    data object OnForgotPwdClicked: LoginAction

}