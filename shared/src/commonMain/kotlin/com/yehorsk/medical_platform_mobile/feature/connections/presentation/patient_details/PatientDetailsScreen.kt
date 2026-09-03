package com.yehorsk.medical_platform_mobile.feature.connections.presentation.patient_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.patient_details.viewmodel.PatientDetailsViewModel

@Composable
fun PatientDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: PatientDetailsViewModel,
    goBack: () -> Unit,
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "${state.patient}"
        )
    }
}