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
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.ChatListScreen
import com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.PatientDashboardScreen
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.ProfileScreen
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.SettingsScreen

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
            ChatListScreen(
                modifier = modifier,
                onConversationClick = {

                },
            )
        }
        composable<Screen.Settings> {
            SettingsScreen(
                modifier = modifier
                    .fillMaxSize(),
                navigateToProfilePage = {
                    navController.navigate(Screen.Profile)
                }
            )
        }
        composable<Screen.Profile>{
            ProfileScreen(
                modifier = modifier
                    .fillMaxSize(),
                onGoBackClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}