package com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.ui.components.AppTopBar
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyHitRegion
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyRegion
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record.component.AnatomyPane
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record.component.CreateMedicalRecordScreenLayout
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record.component.CreateRecordTabs
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record.component.DetailsPane
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record.viewmodel.CreateMedicalRecordViewModel
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record.viewmodel.CreateRecordAction
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record.viewmodel.CreateRecordScreenState
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record.viewmodel.CreateRecordTab
import com.yehorsk.medical_platform_mobile.util.getRole
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