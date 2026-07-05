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
import com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.DoctorDashboardScreen

fun NavGraphBuilder.doctorNavGraph(
    modifier: Modifier = Modifier,
    navController: NavController
){
    navigation<Graph.Doctor>(
        startDestination = Screen.Dashboard
    ){
        composable<Screen.Dashboard> {
            DoctorDashboardScreen(
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