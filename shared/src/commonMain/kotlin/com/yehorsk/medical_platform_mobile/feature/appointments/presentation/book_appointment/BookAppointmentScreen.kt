package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationEventHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import com.yehorsk.medical_platform_mobile.core.ui.components.AppTopBar
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultButton
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultMultilineTextField
import com.yehorsk.medical_platform_mobile.core.util.ObserveAsEvents
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.component.AppointmentCalendar
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.component.AppointmentSummaryCard
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.component.BookingProgress
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.component.SelectedDate
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.component.TimeSlotsFlowRow
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.viewmodel.BookAppointmentAction
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.viewmodel.BookAppointmentEvent
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.viewmodel.BookAppointmentState
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.viewmodel.BookAppointmentViewModel
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.viewmodel.BookingStep
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.book_appointment
import medicalplatformmobile.shared.generated.resources.confirm_appointment
import medicalplatformmobile.shared.generated.resources.reschedule_appointment
import medicalplatformmobile.shared.generated.resources.select_a_time_slot
import medicalplatformmobile.shared.generated.resources.select_date
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookAppointmentScreen(
    modifier: Modifier = Modifier,
    viewModel: BookAppointmentViewModel= koinViewModel(),
    onGoBackClicked: () -> Unit,
    goToAppointmentsScreen: () -> Unit
){
    val navigationState = rememberNavigationEventState(
        currentInfo = NavigationEventInfo.None
    )
    NavigationEventHandler(
        state = navigationState,
        onBackCompleted = {
            viewModel.onAction(BookAppointmentAction.OnGoBackClicked)
        }
    )

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events){ event ->
        when(event){
            is BookAppointmentEvent.NavigateBack -> onGoBackClicked()
            is BookAppointmentEvent.Success -> goToAppointmentsScreen()
        }
    }

    BookAppointmentScreenRoot(
        modifier = modifier,
        state = state,
        onAction = { viewModel.onAction(it) }
    )
}

@Composable
fun BookAppointmentScreenRoot(
    modifier: Modifier = Modifier,
    state: BookAppointmentState,
    onAction: (BookAppointmentAction) -> Unit
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AppTopBar(
            title = stringResource(UiRes.string.book_appointment),
            showGoBackButton = true,
            onGoBackClicked = { onAction(BookAppointmentAction.OnGoBackClicked) }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
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
            Column {
                BookingProgress(
                    modifier = Modifier
                        .padding(vertical = 12.dp),
                    currentStep = state.currentStep
                )
                AnimatedContent(
                    targetState = state.currentStep
                ){ step ->
                    when(step){
                        BookingStep.Doctor -> {

                        }
                        BookingStep.Date -> {
                            Column {
                                AppointmentCalendar(
                                    modifier = Modifier
                                        .padding(vertical = 12.dp),
                                    selectedDate = state.form.selectedDate,
                                    onUpdateSelectedDate = { onAction(BookAppointmentAction.OnDateSelected(it)) },
                                    closedWeekDays = state.openWeekDays,
                                    isLoading = state.isLoadingDates,
                                )
                                DefaultButton(
                                    modifier = Modifier
                                        .padding(vertical = 12.dp),
                                    isEnabled = (state.isConnected),
                                    text = stringResource(UiRes.string.select_date),
                                    onClick = { onAction(BookAppointmentAction.OnGoToNextStateClicked(BookingStep.Time)) }
                                )
                            }
                        }
                        BookingStep.Time -> {
                            Column {
                                SelectedDate(
                                    modifier = Modifier
                                        .padding(vertical = 12.dp),
                                    date = state.form.selectedDate
                                )
                                TimeSlotsFlowRow(
                                    modifier = Modifier
                                        .padding(vertical = 12.dp),
                                    selectedTime = state.form.selectedTime,
                                    times = state.availableTime,
                                    isLoading = state.isLoadingTimes,
                                    onTimeSelected = { onAction(BookAppointmentAction.OnTimeSelected(it)) }
                                )
                                DefaultButton(
                                    modifier = Modifier
                                        .padding(vertical = 12.dp),
                                    isEnabled = (state.form.selectedTime != null && state.isConnected),
                                    text = stringResource(UiRes.string.select_a_time_slot),
                                    onClick = { onAction(BookAppointmentAction.OnGoToNextStateClicked(BookingStep.Confirm)) }
                                )
                            }
                        }
                        BookingStep.Confirm -> {
                            Column {
                                AppointmentSummaryCard(
                                    modifier = Modifier
                                        .padding(vertical = 12.dp),
                                    doctorName = "Dr. Sarah Chen",
                                    specialty = "Cardiology",
                                    date = state.form.selectedDate,
                                    time = state.form.selectedTime ?: ""
                                )
                                DefaultMultilineTextField(
                                    modifier = Modifier
                                        .padding(vertical = 12.dp),
                                    placeholder = "Describe your symptoms or reason for visit...",
                                    header = "NOTE FOR DOCTOR (optional)",
                                    value = state.form.note,
                                    onValueChange = { 
                                        onAction(BookAppointmentAction.OnNoteChanged(it))
                                    }
                                )
                                DefaultButton(
                                    modifier = Modifier
                                        .padding(vertical = 12.dp),
                                    text = if (state.form.appointmentId != null) {
                                        stringResource(UiRes.string.reschedule_appointment)
                                    }else{
                                        stringResource(UiRes.string.confirm_appointment)
                                    },
                                    onClick = {
                                        onAction(BookAppointmentAction.OnCreateAppointmentClicked)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookAppointmentScreenRootPreview(){
    AppTheme {
        BookAppointmentScreenRoot(
            state = BookAppointmentState(
                currentStep = BookingStep.Confirm
            ),
            onAction = {}
        )
    }
}