package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.ui.components.buttons.DefaultAssistChip
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.MedicalRecordType
import com.yehorsk.medical_platform_mobile.util.toDisplayName
import com.yehorsk.theme.AppTheme

@Composable
fun ChooseMedicalRecordType(
    selectedType: MedicalRecordType,
    onSelectRecord: (MedicalRecordType) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        MedicalRecordType.entries.forEach { type ->
            DefaultAssistChip(
                label = type.toDisplayName().asString(),
                isSelected = type == selectedType,
                onClick = { onSelectRecord(type) }
            )
        }
    }
}

@Preview
@Composable
fun ChooseMedicalRecordTypePreview(){
    AppTheme {
        ChooseMedicalRecordType(
            selectedType = MedicalRecordType.CLINICAL_NOTE,
            onSelectRecord = {}
        )
    }
}