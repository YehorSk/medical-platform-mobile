package com.yehorsk.medical_platform_mobile.feature.connections.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.ui.components.AppTopBar
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.main.navigation.ConnectionsMainDestination
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.main.viewmodel.ConnectionsMainViewModel
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.calendar_today_24px
import medicalplatformmobile.shared.generated.resources.connect
import medicalplatformmobile.shared.generated.resources.data_access
import medicalplatformmobile.shared.generated.resources.find_a_doctor
import medicalplatformmobile.shared.generated.resources.hourglass_24px
import medicalplatformmobile.shared.generated.resources.my_appointments
import medicalplatformmobile.shared.generated.resources.my_doctors
import medicalplatformmobile.shared.generated.resources.my_patients
import medicalplatformmobile.shared.generated.resources.patient_list_24px
import medicalplatformmobile.shared.generated.resources.pending_requests
import medicalplatformmobile.shared.generated.resources.search_24px
import medicalplatformmobile.shared.generated.resources.shield_24px
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ConnectionsMainScreen(
    modifier: Modifier = Modifier,
    viewModel: ConnectionsMainViewModel = koinViewModel(),
    role: UserRole,
    navigateTo: (ConnectionsMainDestination) -> Unit

){
    ConnectionsMainScreenRoot(
        modifier = modifier,
        role = role,
        navigateTo = { navigateTo(it) }
    )
}

@Composable
fun ConnectionsMainScreenRoot(
    modifier: Modifier = Modifier,
    role: UserRole,
    navigateTo: (ConnectionsMainDestination) -> Unit
){
    Column(modifier = modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(UiRes.string.connect),
            onGoBackClicked = { navigateTo(ConnectionsMainDestination.Back) }
        )
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    ConnectionListCard(
                        title = stringResource(UiRes.string.my_appointments),
                        subtitle = "",
                        icon = painterResource(UiRes.drawable.calendar_today_24px),
                        onClick = { navigateTo(ConnectionsMainDestination.Appointments) }
                    )
                }
                if(role == UserRole.PATIENT){
                    item {
                        ConnectionListCard(
                            title = stringResource(UiRes.string.find_a_doctor),
                            subtitle = "",
                            icon = painterResource(UiRes.drawable.search_24px),
                            onClick = { navigateTo(ConnectionsMainDestination.FindDoctor) }
                        )
                    }
                }
                if(role == UserRole.PATIENT){
                    item {
                        ConnectionListCard(
                            title = stringResource(UiRes.string.my_doctors),
                            subtitle = "",
                            icon = painterResource(UiRes.drawable.patient_list_24px),
                            onClick = { navigateTo(ConnectionsMainDestination.MyDoctors) }
                        )
                    }
                }
                if(role == UserRole.DOCTOR){
                    item {
                        ConnectionListCard(
                            title = stringResource(UiRes.string.my_patients),
                            subtitle = "",
                            icon = painterResource(UiRes.drawable.patient_list_24px),
                            onClick = { navigateTo(ConnectionsMainDestination.MyPatients) }
                        )
                    }
                }
                item {
                    ConnectionListCard(
                        title = stringResource(UiRes.string.pending_requests),
                        subtitle = "",
                        icon = painterResource(UiRes.drawable.hourglass_24px),
                        onClick = { navigateTo(ConnectionsMainDestination.PendingRequests) }
                    )
                }
                if(role == UserRole.PATIENT){
                    item {
                        ConnectionListCard(
                            title = stringResource(UiRes.string.data_access),
                            subtitle = "",
                            icon = painterResource(UiRes.drawable.shield_24px),
                            onClick = { navigateTo(ConnectionsMainDestination.DataAccess) }
                        )
                    }
                }
            }
        }
    }
}