package com.yehorsk.medical_platform_mobile.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
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
        composable<Screen.Calendar> {
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Calendar"
                )
            }
        }
        composable<Screen.Messages> {
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Messages"
                )
            }
        }
        composable<Screen.Profile> {
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Profile"
                )
            }
        }
    }
}