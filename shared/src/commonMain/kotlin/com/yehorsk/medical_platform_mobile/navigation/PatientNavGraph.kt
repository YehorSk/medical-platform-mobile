package com.yehorsk.medical_platform_mobile.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.ChatListScreen
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.DoctorDetailsScreen
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.viewmodel.DoctorDetailsAction
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.viewmodel.DoctorDetailsViewModel
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.FindDoctorScreen
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.viewmodel.FindDoctorViewModel
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.main.ConnectionsMainScreen
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.main.navigation.ConnectionsMainDestination
import com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.PatientDashboardScreen
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.ProfileScreen
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.SettingsScreen
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.update_password.UpdatePasswordScreen
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.patientNavGraph(
    modifier: Modifier = Modifier,
    navController: NavController
){
    navigation<Graph.Patient>(
        startDestination = Screen.Home
    ){
        composable<Screen.Home> {
            PatientDashboardScreen(
                modifier = modifier
            )
        }
        composable<Screen.Connect> {
            ConnectionsMainScreen(
                modifier = modifier,
                role = UserRole.PATIENT,
                navigateTo = { destination ->
                    when(destination){
                        ConnectionsMainDestination.Back -> navController.popBackStack()
                        ConnectionsMainDestination.Appointments -> {}
                        ConnectionsMainDestination.DataAccess -> {}
                        ConnectionsMainDestination.FindDoctor -> navController.navigate(Screen.FindDoctor)
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
            SettingsScreen(
                modifier = modifier
                    .fillMaxSize(),
                navigateToProfilePage = {
                    navController.navigate(Screen.Profile)
                },
                navigateToUpdatePwdPage = {
                    navController.navigate(Screen.UpdatePwd)
                }
            )
        }
        composable<Screen.FindDoctor>{
            val viewModel: FindDoctorViewModel = koinViewModel()
            FindDoctorScreen(
                modifier = modifier
                    .fillMaxSize(),
                viewModel = viewModel,
                goBack = {
                    navController.popBackStack()
                },
                onDoctorClicked = {
                    navController.navigate(Screen.DoctorDetails(it))
                }
            )
        }
        composable<Screen.DoctorDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.DoctorDetails>()
            val viewModel: DoctorDetailsViewModel = koinViewModel()
            LaunchedEffect(args.doctorId) {
                args.doctorId.let { id ->
                    viewModel.onAction(DoctorDetailsAction.OnGetDoctorById(id))
                }
            }
            DoctorDetailsScreen(
                modifier = modifier
                    .fillMaxSize(),
                goBack = {
                    navController.popBackStack()
                },
                viewModel = viewModel
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
        composable<Screen.UpdatePwd>{
            UpdatePasswordScreen(
                modifier = modifier
                    .fillMaxSize(),
                onGoBackClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}