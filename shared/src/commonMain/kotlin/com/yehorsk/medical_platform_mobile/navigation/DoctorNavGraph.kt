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
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.ChatListScreen
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.main.ConnectionsMainScreen
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.main.navigation.ConnectionsMainDestination
import com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.DoctorDashboardScreen

fun NavGraphBuilder.doctorNavGraph(
    modifier: Modifier = Modifier,
    navController: NavController
){
    navigation<Graph.Doctor>(
        startDestination = Screen.Home
    ){
        composable<Screen.Home> {
            DoctorDashboardScreen(
                modifier = modifier
            )
        }
        composable<Screen.Connect> {
            ConnectionsMainScreen(
                modifier = modifier,
                role = UserRole.DOCTOR,
                navigateTo = { destination ->
                    when(destination){
                        ConnectionsMainDestination.Back -> navController.popBackStack()
                        ConnectionsMainDestination.Appointments -> {}
                        ConnectionsMainDestination.DataAccess -> {}
                        ConnectionsMainDestination.FindDoctor -> {}
                        ConnectionsMainDestination.MyDoctors -> {}
                        ConnectionsMainDestination.PendingRequests -> {}
                    }
                }
            )
        }
        composable<Screen.Records> {
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Records"
                )
            }
        }
        composable<Screen.Chat> {
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