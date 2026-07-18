package com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.domain.model.Specialization
import com.yehorsk.theme.AppTheme

@Composable
fun SpecializationFlowRow(
    modifier: Modifier = Modifier,
    items: List<Specialization>,
    selectedItems: Set<Specialization>,
    onItemClick: (Specialization) -> Unit
){
    FlowRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items.forEach { specialization ->
            FilterChip(
                selected = selectedItems.any { it.id == specialization.id },
                onClick = { onItemClick(specialization) },
                label = {
                    Text(
                        text = specialization.name,
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    labelColor = MaterialTheme.colorScheme.onSurface,
                    iconColor = MaterialTheme.colorScheme.primary
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = false,
                    borderColor = MaterialTheme.colorScheme.outlineVariant
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SpecializationFlowRowPreview() {
    AppTheme {
        SpecializationFlowRow(
            items = listOf(
                Specialization("1", "Cardiology"),
                Specialization("2", "Dermatology"),
                Specialization("3", "Neurology"),
                Specialization("4", "Orthopedics"),
                Specialization("5", "Pediatrics"),
                Specialization("6", "Psychiatry"),
                Specialization("7", "Radiology"),
                Specialization("8", "Urology")
            ),
            selectedItems = setOf(
                Specialization("2", "Dermatology"),
                Specialization("5", "Pediatrics")
            ),
            onItemClick = {}
        )
    }
}