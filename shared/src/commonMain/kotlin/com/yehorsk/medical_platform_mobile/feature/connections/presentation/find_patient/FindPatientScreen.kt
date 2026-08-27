package com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_patient

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
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.component.DoctorCard
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.viewmodel.FindDoctorAction
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_patient.component.PatientCard
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_patient.viewmodel.FindPatientState
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_patient.viewmodel.FindPatientViewModel
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.find_a_doctor
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FindPatientScreen(
    modifier: Modifier = Modifier,
    viewModel: FindPatientViewModel = koinViewModel(),
    goBack: () -> Unit,
    onPatientClicked: (String) -> Unit
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    FindPatientScreenRoot(
        modifier = modifier,
        state = state,
        goBack = goBack
    )
}

@Composable
fun FindPatientScreenRoot(
    modifier: Modifier = Modifier,
    state: FindPatientState,
    goBack: () -> Unit
){
    Column(modifier = modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(UiRes.string.find_a_doctor),
            showGoBackButton = true,
            onGoBackClicked = { goBack() }
        )
        Box(modifier = Modifier.fillMaxSize()) {
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
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.patients, key = { it.id }){ patient ->
                        patient.medicalCard.patient?.let {
                            PatientCard(
                                patient = patient.medicalCard.patient,
                                onClick = {  },
                            )
                        }
                    }
                }
            }
        }
    }
}