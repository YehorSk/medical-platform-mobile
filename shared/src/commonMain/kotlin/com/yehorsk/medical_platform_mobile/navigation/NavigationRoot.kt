package com.yehorsk.medical_platform_mobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.yehorsk.medical_platform_mobile.feature.auth.navigation.authGraph

@Composable
fun NavigationRoot(
    navController: NavHostController,
    startDestination: Any
){
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        authGraph(
            navController = navController
        )
    }
}