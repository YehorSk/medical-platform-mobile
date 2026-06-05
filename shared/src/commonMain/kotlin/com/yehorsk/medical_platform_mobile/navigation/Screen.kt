package com.yehorsk.medical_platform_mobile.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable data object Login : Screen
    @Serializable data object SignUp : Screen
    @Serializable data object ForgotPwd : Screen

    @Serializable data object Dashboard : Screen
    @Serializable data object Calendar : Screen
    @Serializable data object Messages : Screen
    @Serializable data object Profile : Screen

}