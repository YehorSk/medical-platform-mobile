package com.yehorsk.medical_platform_mobile

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.yehorsk.medical_platform_mobile.feature.auth.navigation.AuthGraphRoutes
import com.yehorsk.medical_platform_mobile.navigation.NavigationRoot

@Composable
@Preview
fun App() {

    val navController = rememberNavController()

    MaterialTheme {
        NavigationRoot(
            navController = navController,
            startDestination = AuthGraphRoutes.Graph
        )
    }
}