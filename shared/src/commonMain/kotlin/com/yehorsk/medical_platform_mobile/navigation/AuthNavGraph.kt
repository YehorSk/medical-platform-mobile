package com.yehorsk.medical_platform_mobile.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.email_verification.EmailVerificationScreen
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password.ForgotPasswordScreen
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.LoginScreen
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.RegisterScreen

fun NavGraphBuilder.authGraph(
    navController: NavController
){
    navigation<Graph.Authentication>(
        startDestination = Screen.Login
    ){
        composable<Screen.Login> {
            LoginScreen(
                onSignUpClicked = {
                    navController.navigate(Screen.SignUp){
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                onLoginSuccess = { role ->
                    navigateToMain(navController, role)
                },
                onForgotPwdClicked = {
                    navController.navigate(Screen.ForgotPwd){
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Screen.SignUp> {
            RegisterScreen(
                onSignInClicked = {
                    navController.navigate(Screen.Login){
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                onSignUpClicked = { role ->
                    navigateToMain(navController, role)
                }
            )
        }
        composable<Screen.ForgotPwd> {
            ForgotPasswordScreen(
                onUpdatePwdSuccess = {
                    navController.navigate(Screen.Login){
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Screen.VerifyEmail>(
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "medicalplatform://app/verify-email?token={token}"
                }
            )
        ) {
            EmailVerificationScreen(
                goToLoginPage = {
                    navController.navigate(Screen.Login){
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

private fun navigateToMain(navController: NavController, role: UserRole){
    when(role){
        UserRole.PATIENT -> navController.navigate(Graph.Patient){
            popUpTo(Graph.Authentication){
                inclusive = true
            }
        }
        UserRole.DOCTOR -> navController.navigate(Graph.Doctor){
            popUpTo(Graph.Authentication){
                inclusive = true
            }
        }
        UserRole.ADMIN -> {}
    }
}