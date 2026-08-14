package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.ui.components.AppTopBar
import com.yehorsk.medical_platform_mobile.core.ui.components.NoConnectionBanner
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.component.AppointmentCard
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.viewmodel.AppointmentsListAction
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.viewmodel.AppointmentsListState
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.viewmodel.AppointmentsListViewModel
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.my_appointments
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppointmentsListScreen(
    modifier: Modifier = Modifier,
    viewModel: AppointmentsListViewModel = koinViewModel(),
    onGoBackClicked: () -> Unit,
    onAppointmentClicked: (String) -> Unit
    ){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    AppointmentsListScreenRoot(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when(action){
                is AppointmentsListAction.OnGoBackClicked -> onGoBackClicked()
                is AppointmentsListAction.OnAppointmentClicked -> onAppointmentClicked(action.appointmentId)
            }
        }
    )
}

@Composable
fun AppointmentsListScreenRoot(
    modifier: Modifier = Modifier,
    state: AppointmentsListState,
    onAction: (AppointmentsListAction) -> Unit
){
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        AppTopBar(
            title = stringResource(UiRes.string.my_appointments),
            showGoBackButton = true,
            onGoBackClicked = { onAction(AppointmentsListAction.OnGoBackClicked) }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            if (!state.isConnected) {
                NoConnectionBanner(
                    modifier = Modifier.fillMaxSize()
                )
            }else{
                Column {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.appointments, key = { it.id }){ appointment ->
                            AppointmentCard(
                                appointment = appointment,
                                onClick = { onAction(AppointmentsListAction.OnAppointmentClicked(appointment.id)) }
                            )
                        }
                    }
                }
            }
        }
    }
}