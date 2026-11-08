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
import androidx.navigation.toRoute
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.AppointmentDetailsScreen
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.AppointmentsListScreen
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.BookAppointmentScreen
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.ProfileScreen
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.update_password.UpdatePasswordScreen
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list_detail.ChatListDetailAdaptiveLayout
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_patient.FindPatientScreen
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.main.ConnectionsMainScreen
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.main.navigation.ConnectionsMainDestination
import com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.DoctorDashboardScreen
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.CreateMedicalRecordScreen
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.SettingsScreen
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.doctor_schedule.DoctorScheduleScreen

fun NavGraphBuilder.doctorNavGraph(
    modifier: Modifier = Modifier,
    navController: NavController,
    onChatFullScreenChange: (Boolean) -> Unit = {}
){
    navigation<Graph.Doctor>(
        startDestination = Screen.Home
    ){
        composable<Screen.Home> {
            DoctorDashboardScreen(
                modifier = modifier,
                navigateToChatScreen = { navController.navigate(Screen.Chat) },
                navigateToConversation = { id -> navController.navigate(Screen.ChatDetails(id)) }
            )
        }
        composable<Screen.Connect> {
            ConnectionsMainScreen(
                modifier = modifier,
                role = UserRole.DOCTOR,
                navigateTo = { destination ->
                    when(destination){
                        ConnectionsMainDestination.Back -> navController.popBackStack()
                        ConnectionsMainDestination.Appointments -> navController.navigate(Screen.MyAppointments)
                        ConnectionsMainDestination.DataAccess -> {}
                        ConnectionsMainDestination.MyPatients -> { navController.navigate(Screen.MyPatients)}
                        ConnectionsMainDestination.PendingRequests -> {}
                        else -> {}
                    }
                }
            )
        }
        composable<Screen.Health> {
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
            ChatListDetailAdaptiveLayout(
                modifier = modifier,
                initialChatId = null,
                onFullScreenDetailChange = onChatFullScreenChange
            )
        }
        composable<Screen.ChatDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.ChatDetails>()
            ChatListDetailAdaptiveLayout(
                modifier = modifier
                    .fillMaxSize(),
                initialChatId = args.chatId
            )
        }
        composable<Screen.CreateMedicalRecord> {
            CreateMedicalRecordScreen(
                modifier = modifier
                    .fillMaxSize(),
                goBack = {
                    navController.popBackStack()
                },
                onRecordCreatedSuccessfully = {
                    navController.popBackStack()
                }
            )
        }
        composable<Screen.MyPatients> {
            FindPatientScreen(
                modifier = modifier
                    .fillMaxSize(),
                onPatientClicked = {},
                goBack = {
                    navController.popBackStack()
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
                },
                onLogoutClicked = {
                    navController.navigateToLogin()
                },
                navigateToMySchedulePage = {
                    navController.navigate(Screen.MySchedule)
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
        composable<Screen.UpdatePwd>{
            UpdatePasswordScreen(
                modifier = modifier
                    .fillMaxSize(),
                onGoBackClicked = {
                    navController.popBackStack()
                }
            )
        }
        composable<Screen.BookAppointment> {
            BookAppointmentScreen(
                modifier = modifier
                    .fillMaxSize(),
                onGoBackClicked = {
                    navController.popBackStack()
                },
                goToAppointmentsScreen = {
                    navController.navigate(Screen.MyAppointments) {
                        popUpTo<Screen.Home> {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Screen.MySchedule> {
            DoctorScheduleScreen(
                modifier = modifier
                    .fillMaxSize(),
                onGoBackClicked = {
                    navController.popBackStack()
                },
            )
        }
        composable<Screen.MyAppointments> {
            AppointmentsListScreen(
                modifier = modifier
                    .fillMaxSize(),
                onGoBackClicked = {
                    navController.popBackStack()
                },
                onAppointmentClicked = { appointmentId ->
                    navController.navigate(Screen.AppointmentDetails(appointmentId))
                },
                role = UserRole.DOCTOR
            )
        }
        composable<Screen.AppointmentDetails> {
            AppointmentDetailsScreen(
                modifier = modifier
                    .fillMaxSize(),
                onGoBackClicked = {
                    navController.popBackStack()
                },
                onRescheduleClicked = { doctorId, appointmentId ->
                    navController.navigate(Screen.BookAppointment(doctorId, appointmentId))
                },
                userRole = UserRole.DOCTOR,
                onCreateMedicalRecordClicked = { appointmentId ->
                    navController.navigate(Screen.CreateMedicalRecord(appointmentId))
                }
            )
        }
    }
}