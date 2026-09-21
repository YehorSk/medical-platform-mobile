package com.yehorsk.medical_platform_mobile.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DefaultAssistChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall,
    height: Dp = 28.dp,
    leadingIcon: (@Composable () -> Unit)? = null
) {
    val colors = MaterialTheme.colorScheme
    val contentColor = if (isSelected) colors.onPrimaryContainer else colors.onSurface

    AssistChip(
        onClick = onClick,
        modifier = modifier.height(height),
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (isSelected) colors.primaryContainer else colors.surface,
            labelColor = contentColor,
            leadingIconContentColor = contentColor
        ),
        border = AssistChipDefaults.assistChipBorder(
            enabled = true,
            borderColor = if (isSelected) colors.primary else colors.outline
        ),
        leadingIcon = leadingIcon,
        label = {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = label,
                textAlign = TextAlign.Center,
                style = textStyle
            )
        }
    )
}