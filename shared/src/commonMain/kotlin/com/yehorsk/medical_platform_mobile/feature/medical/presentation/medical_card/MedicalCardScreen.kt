package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yehorsk.medical_platform_mobile.core.ui.components.AppTopBar
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.viewmodel.CreateRecordAction
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.create_record
import medicalplatformmobile.shared.generated.resources.medical_card
import org.jetbrains.compose.resources.stringResource

@Composable
fun MedicalCardScreen(
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
){

}

@Composable
fun MedicalCardScreenRoot(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        AppTopBar(
            title = stringResource(UiRes.string.medical_card),
            showGoBackButton = true,
            onGoBackClicked = {  }
        )
    }
}