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
    // Decided once, on first composition. If it followed live auth state, every login/logout
    // would change the graph, and NavHost would rebuild it and reset the back stack.
    // Later auth changes navigate explicitly (see navigateToLogin / navigateToMain).
    val authStartDestination = remember {
        if (isAuthenticated && userId != null && userRole != null) {
            Screen.LocalAuth(userId = userId, userRole = userRole)
        } else {
            Screen.Login
        }
    }
    val snackbarHostState = LocalSnackbarHostState.current
    // A chat opened inside the Chat tab on a phone stays on the tab's route, so the bar is hidden explicitly
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

/**
 * Opens the login screen and clears the whole back stack, so Back can't return
 * to screens that need a session (logout, session expiry, failed PIN, 401).
 */
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