package com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.ui.components.AppTopBar
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.component.DoctorHeaderCard
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.component.DoctorInfoCard
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.viewmodel.DoctorDetailsState
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.viewmodel.DoctorDetailsViewModel
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.component.DoctorCard
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.about
import medicalplatformmobile.shared.generated.resources.find_a_doctor
import org.jetbrains.compose.resources.stringResource

@Composable
fun DoctorDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DoctorDetailsViewModel,
    goBack: () -> Unit
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    DoctorDetailsScreenRoot(
        modifier = modifier,
        state = state,
        goBack = { goBack() }
    )
}

@Composable
fun DoctorDetailsScreenRoot(
    modifier: Modifier = Modifier,
    state: DoctorDetailsState,
    goBack: () -> Unit
){
    Column(modifier = modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(UiRes.string.find_a_doctor),
            showGoBackButton = true,
            onGoBackClicked = { goBack() }
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
            if(state.doctor != null && state.doctor.user != null){
                Column {
                    DoctorHeaderCard(
                        firstName = state.doctor.user.firstName,
                        lastName = state.doctor.user.firstName,
                        specialization = state.doctor.specialization?.name ?: "----"
                    )
                    DoctorInfoCard(
                        modifier = Modifier
                            .padding(vertical = 12.dp),
                        title = stringResource(UiRes.string.about),
                        content = state.doctor.description
                    )
                }
            }
        }
    }
}