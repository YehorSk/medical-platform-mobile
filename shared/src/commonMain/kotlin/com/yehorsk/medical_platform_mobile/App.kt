package com.yehorsk.medical_platform_mobile

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.yehorsk.medical_platform_mobile.navigation.Graph
import com.yehorsk.medical_platform_mobile.navigation.NavigationRoot
import com.yehorsk.theme.AppTheme

@Composable
@Preview
fun App() {

    val navController = rememberNavController()

    AppTheme {
        NavigationRoot(
            navController = navController,
            startDestination = Graph.Authentication
        )
    }
}