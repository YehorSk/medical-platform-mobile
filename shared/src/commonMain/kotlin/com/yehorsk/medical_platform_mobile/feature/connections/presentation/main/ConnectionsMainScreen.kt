package com.yehorsk.medical_platform_mobile.feature.connections.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.ui.components.layouts.AppTopBar
import com.yehorsk.medical_platform_mobile.core.util.currentWindowLayout
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.main.component.ConnectionsGridCard
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
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
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
    val large = currentWindowLayout().isExpanded
    val menuItems = remember(role) { connectionsMenuItems(role) }

    Column(modifier = modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(UiRes.string.connect),
            onGoBackClicked = { navigateTo(ConnectionsMainDestination.Back) }
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = if (large) 220.dp else 150.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(if (large) 24.dp else 16.dp),
            horizontalArrangement = Arrangement.spacedBy(if (large) 20.dp else 12.dp),
            verticalArrangement = Arrangement.spacedBy(if (large) 20.dp else 12.dp)
        ) {
            items(menuItems, key = { it.destination.toString() }) { item ->
                ConnectionsGridCard(
                    title = stringResource(item.title),
                    icon = painterResource(item.icon),
                    large = large,
                    onClick = { navigateTo(item.destination) }
                )
            }
        }
    }
}

private data class ConnectionsMenuItem(
    val title: StringResource,
    val icon: DrawableResource,
    val destination: ConnectionsMainDestination
)

private fun connectionsMenuItems(role: UserRole): List<ConnectionsMenuItem> = buildList {
    add(ConnectionsMenuItem(UiRes.string.my_appointments, UiRes.drawable.calendar_today_24px, ConnectionsMainDestination.Appointments))
    if (role == UserRole.PATIENT) {
        add(ConnectionsMenuItem(UiRes.string.find_a_doctor, UiRes.drawable.search_24px, ConnectionsMainDestination.FindDoctor))
        add(ConnectionsMenuItem(UiRes.string.my_doctors, UiRes.drawable.patient_list_24px, ConnectionsMainDestination.MyDoctors))
    }
    if (role == UserRole.DOCTOR) {
        add(ConnectionsMenuItem(UiRes.string.my_patients, UiRes.drawable.patient_list_24px, ConnectionsMainDestination.MyPatients))
    }
    add(ConnectionsMenuItem(UiRes.string.pending_requests, UiRes.drawable.hourglass_24px, ConnectionsMainDestination.PendingRequests))
    if (role == UserRole.PATIENT) {
        add(ConnectionsMenuItem(UiRes.string.data_access, UiRes.drawable.shield_24px, ConnectionsMainDestination.DataAccess))
    }
}