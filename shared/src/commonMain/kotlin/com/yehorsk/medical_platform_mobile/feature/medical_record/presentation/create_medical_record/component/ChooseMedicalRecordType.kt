package com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyRegion
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.MedicalRecordType
import com.yehorsk.medical_platform_mobile.util.toDisplayName
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.record_type
import org.jetbrains.compose.resources.stringResource

@Composable
fun ChooseMedicalRecordType(
    modifier: Modifier = Modifier,
    selectedType: MedicalRecordType,
    onSelectRecord: (MedicalRecordType) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(UiRes.string.record_type),
            style = MaterialTheme.typography.bodyLarge
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            MedicalRecordType.entries.forEach { type ->
                val isSelected = type == selectedType

                AssistChip(
                    onClick = { onSelectRecord(type) },
                    modifier = Modifier.height(28.dp),
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (isSelected) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surface
                        },
                        labelColor = if (isSelected) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        },
                        leadingIconContentColor = if (isSelected) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    ),
                    border = AssistChipDefaults.assistChipBorder(
                        enabled = true,
                        borderColor = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.outline
                        }
                    ),
                    label = {
                        val partName = type.name
                            .replace('_', ' ')
                            .lowercase()
                            .replaceFirstChar { it.uppercase() }

                        Text(
                            text = partName,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                )
            }
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