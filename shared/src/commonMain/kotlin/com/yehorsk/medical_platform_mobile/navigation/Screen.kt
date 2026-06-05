package com.yehorsk.medical_platform_mobile.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable data object Login : Screen
    @Serializable data object SignUp : Screen
    @Serializable data object ForgotPwd : Screen

    // Different implementation for Patient and Doctor
    @Serializable data object Dashboard : Screen

}