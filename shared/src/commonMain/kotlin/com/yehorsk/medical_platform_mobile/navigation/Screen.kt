package com.yehorsk.medical_platform_mobile.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable data object Login : Screen
    @Serializable data object SignUp : Screen
    @Serializable data object ForgotPwd : Screen
    @Serializable data class VerifyEmail(
        val token: String
    ) : Screen

    @Serializable data object Home : Screen
    @Serializable data object Connect : Screen
    @Serializable data object Records : Screen
    @Serializable data object Chat : Screen
    @Serializable data object Settings : Screen
    @Serializable data object Profile : Screen
    @Serializable data object UpdatePwd : Screen

    @Serializable data object FindDoctor: Screen

    @Serializable data object PatientDoctors: Screen

    @Serializable data object PendingRequests: Screen

    @Serializable data object DataAccessGDPR: Screen

    @Serializable data class DoctorDetails(
        val doctorId: String
    ): Screen

}