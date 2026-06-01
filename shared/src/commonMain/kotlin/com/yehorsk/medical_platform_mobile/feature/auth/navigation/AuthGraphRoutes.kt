package com.yehorsk.medical_platform_mobile.feature.auth.navigation

import kotlinx.serialization.Serializable

sealed interface AuthGraphRoutes {

    @Serializable
    data object Graph: AuthGraphRoutes

    @Serializable
    data object Login: AuthGraphRoutes

    @Serializable
    data object Register: AuthGraphRoutes

    @Serializable
    data object ForgotPassword: AuthGraphRoutes

}