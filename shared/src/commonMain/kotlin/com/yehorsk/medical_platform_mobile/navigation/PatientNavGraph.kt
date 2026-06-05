package com.yehorsk.medical_platform_mobile.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.PatientDashboardScreen

fun NavGraphBuilder.patientNavGraph(
    modifier: Modifier = Modifier,
    navController: NavController
){
    navigation<Graph.Patient>(
        startDestination = Screen.Dashboard
    ){
        composable<Screen.Dashboard> {
            PatientDashboardScreen(
                modifier = modifier
            )
        }
    }
}