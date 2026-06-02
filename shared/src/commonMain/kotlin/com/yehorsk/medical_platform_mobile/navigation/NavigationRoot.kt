package com.yehorsk.medical_platform_mobile.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.yehorsk.medical_platform_mobile.feature.auth.navigation.authGraph

@Suppress("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationRoot(
    navController: NavHostController,
    startDestination: Any
){
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            authGraph(
                navController = navController
            )
        }
    }
}