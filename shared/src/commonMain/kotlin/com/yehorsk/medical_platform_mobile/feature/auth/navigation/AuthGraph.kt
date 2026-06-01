package com.yehorsk.medical_platform_mobile.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.LoginScreen
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.RegisterScreen

fun NavGraphBuilder.authGraph(
    navController: NavController
){
    navigation<AuthGraphRoutes.Graph>(
        startDestination = AuthGraphRoutes.Login
    ){
        composable<AuthGraphRoutes.Login> {
            LoginScreen(
                onSignUpClicked = {
                    navController.navigate(AuthGraphRoutes.Register){
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<AuthGraphRoutes.Register> {
            RegisterScreen(
                onSignInClicked = {
                    navController.navigate(AuthGraphRoutes.Login){
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}