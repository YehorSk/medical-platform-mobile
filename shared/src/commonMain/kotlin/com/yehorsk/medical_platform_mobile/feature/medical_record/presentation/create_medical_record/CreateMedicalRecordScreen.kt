package com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.yehorsk.medical_platform_mobile.core.ui.components.AppTopBar
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_patient.viewmodel.FindPatientState
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.common.body.BodyMap
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.common.body.BodyPart
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.common.body.Front
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.common.body.frontBodyRegions
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.find_a_doctor
import org.jetbrains.compose.resources.stringResource


@Composable
fun CreateMedicalRecordScreen(
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
){
    CreateMedicalRecordScreenRoot(
        modifier = modifier,
        goBack = { goBack() }
    )
}

@Composable
fun CreateMedicalRecordScreenRoot(
    modifier: Modifier = Modifier,
    goBack: () -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(UiRes.string.find_a_doctor),
            showGoBackButton = true,
            onGoBackClicked = { goBack() }
        )
        var selectedPart by remember { mutableStateOf<BodyPart?>(null) }

        BodyMap(
            regions = frontBodyRegions,
            bodyVector = Front,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .aspectRatio(Front.viewportWidth / Front.viewportHeight, matchHeightConstraintsFirst = true)
                .align(Alignment.CenterHorizontally),
            selected = selectedPart,
            onPartSelected = { region -> selectedPart = region?.part }
        )
    }
}