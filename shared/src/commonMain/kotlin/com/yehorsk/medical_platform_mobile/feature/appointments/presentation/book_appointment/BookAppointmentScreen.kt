package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import com.yehorsk.medical_platform_mobile.core.ui.components.AppTopBar
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultButton
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.component.AppointmentCalendar
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.component.BookingProgress
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.viewmodel.BookAppointmentState
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.viewmodel.BookAppointmentViewModel
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.viewmodel.BookingStep
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.component.DoctorHeaderCard
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.book_appointment
import medicalplatformmobile.shared.generated.resources.select_date
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookAppointmentScreen(
    modifier: Modifier = Modifier,
    viewModel: BookAppointmentViewModel = koinViewModel(),
    onGoBackClicked: () -> Unit
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    BookAppointmentScreenRoot(
        modifier = modifier,
        state = state,
        onGoBackClicked = { onGoBackClicked() }
    )
}

@Composable
fun BookAppointmentScreenRoot(
    modifier: Modifier = Modifier,
    state: BookAppointmentState,
    onGoBackClicked: () -> Unit
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AppTopBar(
            title = stringResource(UiRes.string.book_appointment),
            showGoBackButton = true,
            onGoBackClicked = { onGoBackClicked() }
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
            AnimatedContent(
                targetState = state.currentStep
            ){ step ->
                when(step){
                    BookingStep.Date -> {
                        Column(
                        ) {
                            BookingProgress(
                                modifier = Modifier
                                    .padding(vertical = 12.dp),
                                currentStep = state.currentStep
                            )
                            AppointmentCalendar(
                                modifier = Modifier
                                    .padding(vertical = 12.dp),
                                selectedDate = state.form.selectedDate,
                                onUpdateSelectedDate = {},
                                closedDays = emptyArray<String>()
                            )
                            DefaultButton(
                                modifier = Modifier
                                    .padding(vertical = 12.dp),
                                text = stringResource(UiRes.string.select_date),
                                onClick = {},
                                isEnabled = state.isConnected && !state.isLoading
                            )
                        }
                    }
                    BookingStep.Time -> {}
                    BookingStep.Confirm -> {}
                }
            }
        }
    }
}

@Preview
@Composable
fun BookAppointmentScreenRootPreview(){
    AppTheme {
        BookAppointmentScreenRoot(
            state = BookAppointmentState(

            ),
            onGoBackClicked = {}
        )
    }
}