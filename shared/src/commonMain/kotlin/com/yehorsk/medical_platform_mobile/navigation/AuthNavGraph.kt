package com.yehorsk.medical_platform_mobile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
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
                    when(role){
                        UserRole.PATIENT -> navController.navigate(Graph.Patient){
                            restoreState = true
                            launchSingleTop = true
                        }
                        UserRole.DOCTOR -> navController.navigate(Graph.Doctor){
                            restoreState = true
                            launchSingleTop = true
                        }
                        UserRole.ADMIN -> {}
                    }
                }
            )
        }
    }
}