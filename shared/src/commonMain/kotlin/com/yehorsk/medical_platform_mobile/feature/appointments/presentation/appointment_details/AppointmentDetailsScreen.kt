package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.ui.components.AppTopBar
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultButton
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultInfoCard
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentStatus
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.component.AppointmentInfoCard
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.viewmodel.AppointmentDetailsAction
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.viewmodel.AppointmentDetailsState
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.viewmodel.AppointmentDetailsViewModel
import com.yehorsk.theme.AppTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.appointment
import medicalplatformmobile.shared.generated.resources.appointment_doesnt_exist
import medicalplatformmobile.shared.generated.resources.cancel_btn
import medicalplatformmobile.shared.generated.resources.doctor
import medicalplatformmobile.shared.generated.resources.notes
import medicalplatformmobile.shared.generated.resources.patient
import medicalplatformmobile.shared.generated.resources.reschedule_btn
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppointmentDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: AppointmentDetailsViewModel = koinViewModel(),
    onGoBackClicked: () -> Unit,
    userRole: UserRole
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    AppointmentDetailsScreenRoot(
        modifier = modifier,
        state = state,
        userRole = userRole,
        onAction = { action ->
            when (action) {
                AppointmentDetailsAction.OnGoBackClicked -> {
                    onGoBackClicked()
                }

                AppointmentDetailsAction.OnCancelClicked -> {
                }

                AppointmentDetailsAction.OnRescheduleClicked -> {

                }
            }
        }
    )
}

@Composable
fun AppointmentDetailsScreenRoot(
    modifier: Modifier = Modifier,
    state: AppointmentDetailsState,
    userRole: UserRole,
    onAction: (AppointmentDetailsAction) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        AppTopBar(
            title = stringResource(UiRes.string.appointment),
            showGoBackButton = true,
            onGoBackClicked = {
                onAction(AppointmentDetailsAction.OnGoBackClicked)
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                MaterialTheme.colorScheme.surface.copy(
                                    alpha = 0.7f
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                state.appointment == null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(
                                UiRes.string.appointment_doesnt_exist
                            )
                        )
                    }
                }
                else -> {
                    val appointment = state.appointment

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        AppointmentInfoCard(
                            modifier = Modifier.padding(vertical = 12.dp),
                            appointment = appointment
                        )

                        val doctor = appointment.doctor
                        val patient = appointment.patient

                        if (userRole == UserRole.PATIENT && doctor != null) {
                            DefaultInfoCard(
                                modifier = Modifier.padding(vertical = 12.dp),
                                title = stringResource(UiRes.string.doctor),
                                content = "${doctor.title} ${doctor.firstName} ${doctor.lastName}"
                            )
                        }

                        if (userRole == UserRole.DOCTOR && patient != null) {
                            DefaultInfoCard(
                                modifier = Modifier.padding(vertical = 12.dp),
                                title = stringResource(UiRes.string.patient),
                                content = "${patient.title} ${patient.firstName} ${patient.lastName}"
                            )
                        }

                        DefaultInfoCard(
                            modifier = Modifier.padding(vertical = 12.dp),
                            title = stringResource(UiRes.string.notes),
                            content = appointment.note
                        )

                        DefaultButton(
                            modifier = Modifier.padding(vertical = 12.dp),
                            text = stringResource(UiRes.string.reschedule_btn),
                            isEnabled = state.isConnected,
                            onClick = {
                                onAction(
                                    AppointmentDetailsAction.OnRescheduleClicked
                                )
                            }
                        )

                        DefaultButton(
                            modifier = Modifier.padding(vertical = 12.dp),
                            text = stringResource(UiRes.string.cancel_btn),
                            textColor = MaterialTheme.colorScheme.onErrorContainer,
                            color = MaterialTheme.colorScheme.errorContainer,
                            isEnabled = state.isConnected,
                            onClick = {
                                onAction(
                                    AppointmentDetailsAction.OnCancelClicked
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppointmentDetailsMainPreview() {
    val now = kotlin.time.Clock.System.now()

    val sampleDoctorUser = User(
        id = "u1",
        email = "sarah.chen@example.com",
        firstName = "Sarah",
        lastName = "Chen",
        role = "DOCTOR"
    )

    val appointment = Appointment(
        id = "1",
        doctor = sampleDoctorUser,
        status = AppointmentStatus.PENDING,
        specialization = "Dermatologist",
        note = "",
        date = LocalDate(2026, 7, 22),
        time = LocalTime(14, 0),
        createdAt = now,
        updatedAt = now
    )

    AppTheme {
        AppointmentDetailsScreenRoot(
            state = AppointmentDetailsState(
                appointment = appointment
            ),
            userRole = UserRole.PATIENT,
            onAction = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppointmentDetailsEmptyAppointmentPreview() {
    AppTheme {
        AppointmentDetailsScreenRoot(
            state = AppointmentDetailsState(
                isLoading = false
            ),
            userRole = UserRole.PATIENT,
            onAction = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppointmentDetailsLoadingPreview() {
    AppTheme {
        AppointmentDetailsScreenRoot(
            state = AppointmentDetailsState(
                isLoading = true
            ),
            userRole = UserRole.PATIENT,
            onAction = {}
        )
    }
}