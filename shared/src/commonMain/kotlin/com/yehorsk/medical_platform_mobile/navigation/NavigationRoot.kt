package com.yehorsk.medical_platform_mobile.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.ui.components.layouts.MainScaffold
import com.yehorsk.medical_platform_mobile.core.util.LocalSnackbarHostState
import kotlinx.serialization.Serializable

@Composable
fun NavigationRoot(
    navController: NavHostController,
    startDestination: Graph = Graph.Authentication,
    isAuthenticated: Boolean,
    userId: String?,
    userRole: UserRole?
){
    val authStartDestination = remember {
        if (isAuthenticated && userId != null && userRole != null) {
            Screen.LocalAuth(userId = userId, userRole = userRole)
        } else {
            Screen.Login
        }
    }
    val snackbarHostState = LocalSnackbarHostState.current
    var isChatFullScreen by remember { mutableStateOf(false) }
    MainScaffold(
        modifier = Modifier
            .fillMaxSize(),
        navController = navController,
        snackbarHostState = snackbarHostState,
        userRole = userRole,
        hideNavigation = isChatFullScreen,
        screenContent = {
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                authGraph(
                    navController = navController,
                    startDestination = authStartDestination
                )
                patientNavGraph(
                    navController = navController,
                    onChatFullScreenChange = { isChatFullScreen = it }
                )
                doctorNavGraph(
                    navController = navController,
                    onChatFullScreenChange = { isChatFullScreen = it }
                )
            }
        }
    )
}

fun NavController.navigateToLogin() {
    navigate(Screen.Login) {
        popUpTo(0) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

@Serializable
sealed class Graph{
    @Serializable data object Root: Graph()
    @Serializable data object Authentication: Graph()
    @Serializable data object Patient: Graph()
    @Serializable data object Doctor: Graph()
}