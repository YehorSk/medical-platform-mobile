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
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyPart
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.common.body.BackAnatomy
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.common.body.BodyMap
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.common.body.backBodyRegions
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
        var selectedParts by remember { mutableStateOf<List<BodyPart>>(emptyList()) }

        BodyMap(
            regions = backBodyRegions,
            bodyVector = BackAnatomy,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .aspectRatio(
                    BackAnatomy.viewportWidth / BackAnatomy.viewportHeight,
                    matchHeightConstraintsFirst = true
                )
                .align(Alignment.CenterHorizontally),
            selected = selectedParts,
            onPartSelected = { region ->
                val part = region?.part ?: return@BodyMap
                selectedParts = if (part in selectedParts) {
                    selectedParts - part
                } else {
                    selectedParts + part
                }
            }
        )
    }
}