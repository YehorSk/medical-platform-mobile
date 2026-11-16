package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.ui.components.layouts.AppTopBar
import com.yehorsk.medical_platform_mobile.core.ui.components.other.NoConnectionBanner
import com.yehorsk.medical_platform_mobile.core.util.currentWindowLayout
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.component.AppointmentCard
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.component.AppointmentsViewTabs
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.component.calendar.AppointmentsCalendar
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.viewmodel.AppointmentsListAction
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.viewmodel.AppointmentsListState
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.viewmodel.AppointmentsListViewModel
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.viewmodel.AppointmentsViewMode
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.my_appointments
import medicalplatformmobile.shared.generated.resources.refresh
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppointmentsListScreen(
    modifier: Modifier = Modifier,
    viewModel: AppointmentsListViewModel = koinViewModel(),
    onGoBackClicked: () -> Unit,
    onAppointmentClicked: (String) -> Unit,
    role: UserRole
    ){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME){
        viewModel.onResume()
    }

    AppointmentsListScreenRoot(
        modifier = modifier,
        state = state,
        userRole = role,
        onAction = { action ->
            when(action){
                is AppointmentsListAction.OnGoBackClicked -> onGoBackClicked()
                is AppointmentsListAction.OnAppointmentClicked -> onAppointmentClicked(action.appointmentId)
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun AppointmentsListScreenRoot(
    modifier: Modifier = Modifier,
    state: AppointmentsListState,
    onAction: (AppointmentsListAction) -> Unit,
    userRole: UserRole
){
    // Doctors on wide screens can switch to a week calendar; phones always get the list
    val canShowCalendar = userRole == UserRole.DOCTOR && currentWindowLayout().isExpanded
    val showCalendar = canShowCalendar && state.viewMode == AppointmentsViewMode.CALENDAR

    LaunchedEffect(showCalendar) {
        if (showCalendar) onAction(AppointmentsListAction.OnCalendarShown)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        AppTopBar(
            title = stringResource(UiRes.string.my_appointments),
            showGoBackButton = true,
            onGoBackClicked = { onAction(AppointmentsListAction.OnGoBackClicked) }
        )
        // The calendar has refresh in its own header; this row is only for the list on desktop
        if (canShowCalendar && !showCalendar) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Calendar/List switch is hidden for now, desktop always shows the calendar
//                AppointmentsViewTabs(
//                    modifier = Modifier.width(260.dp),
//                    selectedMode = state.viewMode,
//                    onModeSelected = { onAction(AppointmentsListAction.OnViewModeChanged(it)) }
//                )
                Spacer(Modifier.weight(1f))
                RefreshButton(
                    isRefreshing = state.isRefreshing,
                    enabled = !state.isLoading,
                    onClick = { onAction(AppointmentsListAction.OnRefreshClicked) }
                )
            }
        }
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
            }else if(!state.isConnected){
                NoConnectionBanner(
                    modifier = Modifier.fillMaxSize()
                )
            }else if(showCalendar){
                AppointmentsCalendar(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    mode = state.calendarMode,
                    selectedDate = state.selectedDate,
                    appointments = state.appointments,
                    daySchedules = state.daySchedules,
                    onModeChange = { onAction(AppointmentsListAction.OnCalendarModeChanged(it)) },
                    onSelectedDateChange = { onAction(AppointmentsListAction.OnSelectedDateChanged(it)) },
                    onDayClick = { onAction(AppointmentsListAction.OnDayClicked(it)) },
                    onAppointmentClick = { onAction(AppointmentsListAction.OnAppointmentClicked(it)) },
                    headerActions = {
                        Spacer(Modifier.width(8.dp))
                        RefreshButton(
                            isRefreshing = state.isRefreshing,
                            enabled = !state.isLoading,
                            onClick = { onAction(AppointmentsListAction.OnRefreshClicked) }
                        )
                    }
                )
            }else{
                Column {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.appointments, key = { it.id }){ appointment ->
                            AppointmentCard(
                                appointment = appointment,
                                role = userRole,
                                onClick = { onAction(AppointmentsListAction.OnAppointmentClicked(appointment.id)) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RefreshButton(
    isRefreshing: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    if (isRefreshing) {
        CircularProgressIndicator(
            modifier = Modifier
                .padding(12.dp)
                .size(24.dp),
            strokeWidth = 2.dp
        )
    } else {
        IconButton(
            enabled = enabled,
            onClick = onClick
        ) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = stringResource(UiRes.string.refresh)
            )
        }
    }
}