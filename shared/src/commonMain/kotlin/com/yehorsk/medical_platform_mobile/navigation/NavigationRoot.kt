package com.yehorsk.medical_platform_mobile.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.yehorsk.medical_platform_mobile.core.ui.components.BottomBar
import kotlinx.serialization.Serializable

@Composable
fun NavigationRoot(
    navController: NavHostController,
    startDestination: Graph
){
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            BottomBar(
                navController = navController
            )
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            authGraph(
                navController = navController
            )
            patientNavGraph(
                modifier = Modifier.padding(paddingValues),
                navController = navController
            )
            doctorNavGraph(
                modifier = Modifier.padding(paddingValues),
                navController = navController
            )
        }
    }
}

@Serializable
sealed class Graph{
    @Serializable data object Root: Graph()
    @Serializable data object Authentication: Graph()
    @Serializable data object Patient: Graph()
    @Serializable data object Doctor: Graph()
}