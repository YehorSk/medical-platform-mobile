package com.yehorsk.medical_platform_mobile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.email_verification.EmailVerificationScreen
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password.ForgotPasswordScreen
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.LoginScreen
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.RegisterScreen
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register_success.RegisterSuccessScreen
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register_success.viewmodel.RegisterSuccessScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.authGraph(
    navController: NavController
){
    navigation<Graph.Authentication>(
        startDestination = Screen.Login
    ){
        composable<Screen.Login> {
            LoginScreen(
                onSignUpClicked = {
                    navController.navigate(Screen.Register){
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
        composable<Screen.Register> {
            RegisterScreen(
                onSignInClicked = {
                    navController.navigate(Screen.Login){
                        popUpTo(Screen.Register) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                onRegisterSuccess = {
                    navController.navigate(Screen.RegisterSuccess(it))
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
        composable<Screen.RegisterSuccess> {
            RegisterSuccessScreen(
                onLoginClick = {
                    navController.navigate(Screen.Login){
                        popUpTo<Screen.RegisterSuccess> {
                            inclusive = true
                        }
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
                        popUpTo<Screen.VerifyEmail> {
                            inclusive = true
                        }
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