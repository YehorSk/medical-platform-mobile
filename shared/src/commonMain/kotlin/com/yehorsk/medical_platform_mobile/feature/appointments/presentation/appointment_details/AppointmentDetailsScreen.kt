package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.ui.components.layouts.AppTopBar
import com.yehorsk.medical_platform_mobile.core.ui.components.buttons.DefaultButton
import com.yehorsk.medical_platform_mobile.core.ui.components.cards.DefaultInfoCard
import com.yehorsk.medical_platform_mobile.core.ui.components.dialogs.DestructiveConfirmationDialog
import com.yehorsk.medical_platform_mobile.core.util.currentWindowLayout
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentDoctor
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentStatus
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.component.AppointmentInfoCard
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.component.AppointmentPatientCard
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.viewmodel.AppointmentDetailsAction
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.viewmodel.AppointmentDetailsState
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.viewmodel.AppointmentDetailsViewModel
import com.yehorsk.theme.AppTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.add_24px
import medicalplatformmobile.shared.generated.resources.appointment
import medicalplatformmobile.shared.generated.resources.appointment_doesnt_exist
import medicalplatformmobile.shared.generated.resources.cancel_btn
import medicalplatformmobile.shared.generated.resources.cancel_appointment_btn
import medicalplatformmobile.shared.generated.resources.cancel_status_description
import medicalplatformmobile.shared.generated.resources.cancel_status_title
import medicalplatformmobile.shared.generated.resources.complete_status_description
import medicalplatformmobile.shared.generated.resources.complete_status_title
import medicalplatformmobile.shared.generated.resources.confirm
import medicalplatformmobile.shared.generated.resources.confirm_status_description
import medicalplatformmobile.shared.generated.resources.confirm_status_title
import medicalplatformmobile.shared.generated.resources.go_back_btn
import medicalplatformmobile.shared.generated.resources.reject_appointment_description
import medicalplatformmobile.shared.generated.resources.reject_appointment_title
import medicalplatformmobile.shared.generated.resources.reject_btn
import medicalplatformmobile.shared.generated.resources.create_medical_record
import medicalplatformmobile.shared.generated.resources.doctor
import medicalplatformmobile.shared.generated.resources.mark_completed
import medicalplatformmobile.shared.generated.resources.notes
import medicalplatformmobile.shared.generated.resources.reschedule_btn
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock

@Composable
fun AppointmentDetailsScreen(
    modifier: Modifier = Modifier,
    userRole: UserRole,
    viewModel: AppointmentDetailsViewModel = koinViewModel(),
    onGoBackClicked: () -> Unit,
    onCreateMedicalRecordClicked: (String) -> Unit = {},
    onRescheduleClicked: (String, String) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME){
        viewModel.onResume()
    }

    AppointmentDetailsScreenRoot(
        modifier = modifier,
        state = state,
        userRole = userRole,
        onAction = { action ->
            when (action) {
                AppointmentDetailsAction.OnGoBackClicked -> {
                    onGoBackClicked()
                }
                AppointmentDetailsAction.OnRescheduleClicked -> {
                    state.appointment?.let { appointment ->
                        val doctorId = appointment.doctor?.id
                            ?: return@let

                        onRescheduleClicked(doctorId, appointment.id)
                    }
                }
                AppointmentDetailsAction.OnCreateMedicalRecordClicked -> {
                    state.appointment?.let { appointment ->
                        onCreateMedicalRecordClicked(appointment.id)
                    }
                }
                else -> {viewModel.onAction(action)}
            }
        }
    )
}

@Composable
fun AppointmentDetailsScreenRoot(
    modifier: Modifier = Modifier,
    state: AppointmentDetailsState,
    onAction: (AppointmentDetailsAction) -> Unit,
    userRole: UserRole
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
                    val isTwoPane = currentWindowLayout().isExpanded

                    if (isTwoPane) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Row(
                                modifier = Modifier
                                    .widthIn(max = MaxTwoPaneWidth)
                                    .fillMaxSize(),
                                horizontalArrangement = Arrangement.spacedBy(24.dp)
                            ) {
                                AppointmentDetailsContent(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .verticalScroll(rememberScrollState())
                                        .padding(vertical = 12.dp),
                                    appointment = appointment,
                                    userRole = userRole
                                )
                                if (appointment.availableActions(userRole).isNotEmpty()) {
                                    Surface(
                                        modifier = Modifier
                                            .width(ActionsPaneWidth)
                                            .padding(vertical = 12.dp),
                                        shape = RoundedCornerShape(16.dp),
                                        color = MaterialTheme.colorScheme.surfaceContainerLow
                                    ) {
                                        AppointmentDetailsActions(
                                            modifier = Modifier
                                                .verticalScroll(rememberScrollState())
                                                .padding(16.dp),
                                            appointment = appointment,
                                            userRole = userRole,
                                            isConnected = state.isConnected,
                                            onAction = onAction
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(vertical = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            AppointmentDetailsContent(
                                appointment = appointment,
                                userRole = userRole
                            )
                            AppointmentDetailsActions(
                                appointment = appointment,
                                userRole = userRole,
                                isConnected = state.isConnected,
                                onAction = onAction
                            )
                        }
                    }
                }
            }
        }
    }
    state.pendingStatusChange?.let { status ->
        val texts = status.confirmationTexts()
        if (texts != null) {
            DestructiveConfirmationDialog(
                title = stringResource(texts.title),
                description = stringResource(texts.description),
                confirmButtonText = stringResource(texts.confirmButton),
                cancelButtonText = stringResource(UiRes.string.go_back_btn),
                isConfirmEnabled = state.isConnected,
                onConfirmClick = { onAction(AppointmentDetailsAction.OnStatusChangeConfirmed) },
                onCancelClick = { onAction(AppointmentDetailsAction.OnStatusChangeDismissed) },
                onDismiss = { onAction(AppointmentDetailsAction.OnStatusChangeDismissed) }
            )
        }
    }
//    if(state.showBottomSheet){
//        CompleteAppointmentBottomSheet(
//            onDismiss = {
//                onAction(
//                    AppointmentDetailsAction.ShowBottomSheet
//                )
//            },
//            onCreateMedicalRecordClicked = {},
//            onJustMarkCompleteClicked = {},
//            onCancelClicked = {
//                onAction(
//                    AppointmentDetailsAction.ShowBottomSheet
//                )
//            }
//        )
//    }
}

private val MaxTwoPaneWidth = 1200.dp
private val ActionsPaneWidth = 340.dp

@Composable
private fun AppointmentDetailsContent(
    modifier: Modifier = Modifier,
    appointment: Appointment,
    userRole: UserRole
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        AppointmentInfoCard(appointment = appointment)

        val doctor = appointment.doctor
        val patient = appointment.patient
        when (userRole) {
            UserRole.PATIENT if doctor != null -> {
                DefaultInfoCard(
                    title = stringResource(UiRes.string.doctor),
                    content = "${doctor.title} ${doctor.firstName} ${doctor.lastName}"
                )
            }
            UserRole.DOCTOR if patient != null -> {
                AppointmentPatientCard(appointment = appointment)
            }
            else -> {
                DefaultInfoCard(
                    title = "No Data",
                    content = "No Data"
                )
            }
        }

        DefaultInfoCard(
            title = stringResource(UiRes.string.notes),
            content = appointment.note
        )
    }
}

@Composable
private fun AppointmentDetailsActions(
    modifier: Modifier = Modifier,
    appointment: Appointment,
    userRole: UserRole,
    isConnected: Boolean,
    onAction: (AppointmentDetailsAction) -> Unit
) {
    val actions = appointment.availableActions(userRole)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (AppointmentDetailsUiAction.CONFIRM in actions) {
            DefaultButton(
                text = stringResource(UiRes.string.confirm),
                enabled = isConnected,
                color = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary,
                onClick = {
                    onAction(AppointmentDetailsAction.OnStatusChangeRequested(AppointmentStatus.CONFIRMED))
                }
            )
        }
        if (AppointmentDetailsUiAction.REJECT in actions) {
            DefaultButton(
                text = stringResource(UiRes.string.reject_btn),
                enabled = isConnected,
                textColor = MaterialTheme.colorScheme.onErrorContainer,
                color = MaterialTheme.colorScheme.errorContainer,
                onClick = {
                    onAction(AppointmentDetailsAction.OnStatusChangeRequested(AppointmentStatus.REJECTED))
                }
            )
        }
        if (AppointmentDetailsUiAction.MARK_COMPLETED in actions) {
            DefaultButton(
                text = stringResource(UiRes.string.mark_completed),
                enabled = isConnected,
                onClick = {
                    onAction(AppointmentDetailsAction.OnStatusChangeRequested(AppointmentStatus.COMPLETED))
                }
            )
        }
        if (AppointmentDetailsUiAction.CREATE_MEDICAL_RECORD in actions) {
            DefaultButton(
                onClick = {
                    onAction(AppointmentDetailsAction.OnCreateMedicalRecordClicked)
                },
                enabled = isConnected,
                text = stringResource(UiRes.string.create_medical_record),
                leadingIcon = painterResource(UiRes.drawable.add_24px),
                color = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary,
            )
        }
        if (AppointmentDetailsUiAction.RESCHEDULE in actions) {
            DefaultButton(
                text = stringResource(UiRes.string.reschedule_btn),
                enabled = isConnected,
                onClick = {
                    onAction(AppointmentDetailsAction.OnRescheduleClicked)
                }
            )
        }
        if (AppointmentDetailsUiAction.CANCEL in actions) {
            DefaultButton(
                text = stringResource(UiRes.string.cancel_btn),
                textColor = MaterialTheme.colorScheme.onErrorContainer,
                color = MaterialTheme.colorScheme.errorContainer,
                enabled = isConnected,
                onClick = {
                    onAction(AppointmentDetailsAction.OnStatusChangeRequested(AppointmentStatus.CANCELLED))
                }
            )
        }
    }
}

private class StatusChangeTexts(
    val title: StringResource,
    val description: StringResource,
    val confirmButton: StringResource
)

private fun AppointmentStatus.confirmationTexts(): StatusChangeTexts? = when (this) {
    AppointmentStatus.CONFIRMED -> StatusChangeTexts(
        UiRes.string.confirm_status_title,
        UiRes.string.confirm_status_description,
        UiRes.string.confirm
    )
    AppointmentStatus.REJECTED -> StatusChangeTexts(
        UiRes.string.reject_appointment_title,
        UiRes.string.reject_appointment_description,
        UiRes.string.reject_btn
    )
    AppointmentStatus.COMPLETED -> StatusChangeTexts(
        UiRes.string.complete_status_title,
        UiRes.string.complete_status_description,
        UiRes.string.mark_completed
    )
    AppointmentStatus.CANCELLED -> StatusChangeTexts(
        UiRes.string.cancel_status_title,
        UiRes.string.cancel_status_description,
        UiRes.string.cancel_appointment_btn
    )
    else -> null
}

private enum class AppointmentDetailsUiAction {
    CONFIRM, REJECT, MARK_COMPLETED, CREATE_MEDICAL_RECORD, RESCHEDULE, CANCEL
}

/**
 * Which actions the UI offers for this appointment. This is usability only;
 * the backend must still enforce the allowed status transitions.
 */
private fun Appointment.availableActions(userRole: UserRole): Set<AppointmentDetailsUiAction> {
    val isDoctor = userRole == UserRole.DOCTOR
    val isUpcoming = status == AppointmentStatus.PENDING || status == AppointmentStatus.CONFIRMED
    return buildSet {
        if (isDoctor && status == AppointmentStatus.PENDING) {
            add(AppointmentDetailsUiAction.CONFIRM)
            add(AppointmentDetailsUiAction.REJECT)
        }
        if (isDoctor && status == AppointmentStatus.CONFIRMED) {
            add(AppointmentDetailsUiAction.MARK_COMPLETED)
        }
        if (isDoctor && (status == AppointmentStatus.CONFIRMED || status == AppointmentStatus.COMPLETED)) {
            add(AppointmentDetailsUiAction.CREATE_MEDICAL_RECORD)
        }
        if (isUpcoming && doctor?.id != null) {
            add(AppointmentDetailsUiAction.RESCHEDULE)
        }
        if (isUpcoming) {
            add(AppointmentDetailsUiAction.CANCEL)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppointmentDetailsMainPreview() {
    val appointment = Appointment(
        id = "1",
        doctor = AppointmentDoctor(
            id = "",
            firstName = "John",
            lastName = "Smith",
            title = "Bc.",
            specialization = "Surgeon"
        ),

        status = AppointmentStatus.CONFIRMED,
        note = "",
        date = LocalDate(2026, 7, 18),
        time = LocalTime(10, 30),
        createdAt = Clock.System.now(),
        updatedAt = Clock.System.now()
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