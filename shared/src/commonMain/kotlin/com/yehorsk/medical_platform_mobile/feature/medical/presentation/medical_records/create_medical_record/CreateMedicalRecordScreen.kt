package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.ui.components.layouts.AppTopBar
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.component.CreateMedicalRecordScreenLayout
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.viewmodel.CreateMedicalRecordViewModel
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.viewmodel.CreateRecordAction
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.viewmodel.CreateRecordScreenState
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.create_record
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun CreateMedicalRecordScreen(
    viewModel: CreateMedicalRecordViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
){

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CreateMedicalRecordScreenRoot(
        modifier = modifier,
        state = state,
        onAction = {
            when(it){
                CreateRecordAction.OnGoBackClicked -> goBack()
                else -> viewModel.onAction(it)
            }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateMedicalRecordScreenRoot(
    state: CreateRecordScreenState,
    modifier: Modifier = Modifier,
    onAction: (CreateRecordAction) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        AppTopBar(
            title = stringResource(UiRes.string.create_record),
            showGoBackButton = true,
            onGoBackClicked = { onAction(CreateRecordAction.OnGoBackClicked) }
        )
        CreateMedicalRecordScreenLayout(
            state = state,
            onAction = onAction,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview
@Composable
fun CreateMedicalRecordScreenRootPreview(){
    AppTheme {
        CreateMedicalRecordScreenRoot(
            state = CreateRecordScreenState()
        ) {  }
    }
}