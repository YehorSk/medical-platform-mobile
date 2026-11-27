package com.yehorsk.medical_platform_mobile.core.ui.components.buttons

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
import com.yehorsk.theme.AppTheme

// Wrapping row of multi-select chips, used by filter sheets
@Composable
fun <T> DefaultFilterChipRow(
    modifier: Modifier = Modifier,
    items: List<T>,
    isSelected: (T) -> Boolean,
    label: @Composable (T) -> String,
    onItemClick: (T) -> Unit
){
    FlowRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items.forEach { item ->
            FilterChip(
                selected = isSelected(item),
                onClick = { onItemClick(item) },
                label = {
                    Text(
                        text = label(item),
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
private fun DefaultFilterChipRowPreview() {
    AppTheme {
        DefaultFilterChipRow(
            items = listOf("Cardiology", "Dermatology", "Neurology", "Pediatrics"),
            isSelected = { it == "Dermatology" },
            label = { it },
            onItemClick = {}
        )
    }
}
