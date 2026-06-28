package com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password.viewmodel

sealed interface ForgotPasswordAction {

    data class UpdateEmail(val email: String): ForgotPasswordAction
    data class UpdateCode(val code: String): ForgotPasswordAction
    data class UpdatePassword(val password: String): ForgotPasswordAction
    data class UpdatePasswordConfirm(val password: String): ForgotPasswordAction
    data object OnSendResetTokenClicked: ForgotPasswordAction
    data object OnChangePwdVisibilityClicked: ForgotPasswordAction
    data object OnSendCodeClicked: ForgotPasswordAction
    data object OnSendNewPwdClicked: ForgotPasswordAction

}