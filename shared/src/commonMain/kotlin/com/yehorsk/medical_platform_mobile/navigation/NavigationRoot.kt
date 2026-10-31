package com.yehorsk.medical_platform_mobile.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    val snackbarHostState = LocalSnackbarHostState.current
    MainScaffold(
        modifier = Modifier
            .fillMaxSize(),
        navController = navController,
        snackbarHostState = snackbarHostState,
        userRole = userRole,
        screenContent = {
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                authGraph(
                    navController = navController,
                    isAuthenticated = isAuthenticated,
                    userId = userId,
                    userRole = userRole
                )
                patientNavGraph(
                    navController = navController
                )
                doctorNavGraph(
                    navController = navController
                )
            }
        }
    )
}

@Serializable
sealed class Graph{
    @Serializable data object Root: Graph()
    @Serializable data object Authentication: Graph()
    @Serializable data object Patient: Graph()
    @Serializable data object Doctor: Graph()
}