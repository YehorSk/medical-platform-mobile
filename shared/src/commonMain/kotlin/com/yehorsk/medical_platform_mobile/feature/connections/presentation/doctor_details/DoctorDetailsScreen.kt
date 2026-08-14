package com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.domain.model.AccessStatus
import com.yehorsk.medical_platform_mobile.core.domain.model.Doctor
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.ui.components.AppTopBar
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultButton
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.component.DoctorHeaderCard
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultInfoCard
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.component.WeeklyScheduleCard
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.viewmodel.DoctorDetailsAction
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.viewmodel.DoctorDetailsState
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.viewmodel.DoctorDetailsViewModel
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.about
import medicalplatformmobile.shared.generated.resources.approve_access
import medicalplatformmobile.shared.generated.resources.decline_access
import medicalplatformmobile.shared.generated.resources.find_doctors
import medicalplatformmobile.shared.generated.resources.grant_access
import medicalplatformmobile.shared.generated.resources.open_chat_with_doctor
import medicalplatformmobile.shared.generated.resources.revoke_access
import org.jetbrains.compose.resources.stringResource

@Composable
fun DoctorDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DoctorDetailsViewModel,
    goBack: () -> Unit,
    onBookAppointmentClicked: (Doctor) -> Unit
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    DoctorDetailsScreenRoot(
        modifier = modifier,
        state = state,
       onAction = { action ->
           when(action){
               is DoctorDetailsAction.GoBackClicked -> {
                   goBack()
               }
               is DoctorDetailsAction.OnBookAppointmentClicked -> {
                   state.doctorDetails?.let {
                       onBookAppointmentClicked(it)
                   }
               }
               else -> {
                   viewModel.onAction(action)
               }
           }
       }
    )
}

@Composable
fun DoctorDetailsScreenRoot(
    modifier: Modifier = Modifier,
    state: DoctorDetailsState,
    onAction: (DoctorDetailsAction) -> Unit
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AppTopBar(
            title = stringResource(UiRes.string.find_doctors),
            showGoBackButton = true,
            onGoBackClicked = { onAction(DoctorDetailsAction.GoBackClicked) }
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
        ) {
            if (state.isLoadingDoctor) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            if(state.doctorDetails != null){
                Column {
                    DoctorHeaderCard(
                        firstName = state.doctorDetails.user?.firstName ?: "",
                        lastName = state.doctorDetails.user?.lastName ?: "",
                        specialization = state.doctorDetails.specialization?.name ?: "----"
                    )
                    DefaultInfoCard(
                        modifier = Modifier
                            .padding(vertical = 12.dp),
                        title = stringResource(UiRes.string.about),
                        content = state.doctorDetails.description
                    )
                    WeeklyScheduleCard(
                        modifier = Modifier
                            .padding(vertical = 12.dp),
                        daySchedule = state.doctorDetails.daySchedules,
                        onBookClicked = {onAction(DoctorDetailsAction.OnBookAppointmentClicked)}
                    )
                    val accessStatus = state.patientAccess?.status
                    val initiatedBy = state.patientAccess?.initiatedBy
                    val showGiveAccess = accessStatus == null ||
                            listOf(AccessStatus.UNKNOWN, AccessStatus.REVOKED, AccessStatus.REJECTED).contains(accessStatus)
                    val showPendingStatusByDoctor = accessStatus == AccessStatus.PENDING && initiatedBy == UserRole.DOCTOR
                    val showApprovedAccess = accessStatus == AccessStatus.APPROVED
                    if(showGiveAccess){
                        DefaultButton(
                            modifier = Modifier
                                .padding(vertical = 12.dp),
                            isEnabled = state.isConnected,
                            onClick = { onAction(DoctorDetailsAction.OnGrantAccessClicked) },
                            text = stringResource(UiRes.string.grant_access)
                        )
                    }
                    if(showApprovedAccess){
                        DefaultButton(
                            modifier = Modifier
                                .padding(vertical = 12.dp),
                            isEnabled = state.isConnected,
                            onClick = { onAction(DoctorDetailsAction.OnOpenChatClicked) },
                            text = stringResource(UiRes.string.open_chat_with_doctor)
                        )
                        DefaultButton(
                            modifier = Modifier
                                .padding(vertical = 12.dp),
                            isEnabled = state.isConnected,
                            onClick = { onAction(DoctorDetailsAction.OnRevokeAccessClicked) },
                            text = stringResource(UiRes.string.revoke_access)
                        )
                    }
                    if(showPendingStatusByDoctor){
                        DefaultButton(
                            modifier = Modifier
                                .padding(vertical = 12.dp),
                            isEnabled = state.isConnected,
                            onClick = { onAction(DoctorDetailsAction.OnApproveAccessClicked) },
                            text = stringResource(UiRes.string.approve_access)
                        )
                        DefaultButton(
                            modifier = Modifier
                                .padding(vertical = 12.dp),
                            isEnabled = state.isConnected,
                            onClick = { onAction(DoctorDetailsAction.OnRejectAccessClicked) },
                            text = stringResource(UiRes.string.decline_access)
                        )
                    }
                }
            }
        }
    }
}