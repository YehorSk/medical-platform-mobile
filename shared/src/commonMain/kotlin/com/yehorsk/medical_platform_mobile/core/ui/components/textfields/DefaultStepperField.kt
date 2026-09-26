package com.yehorsk.medical_platform_mobile.core.ui.components.textfields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.add_24px
import medicalplatformmobile.shared.generated.resources.remove_24px
import org.jetbrains.compose.resources.painterResource

@Composable
fun DefaultStepperField(
    modifier: Modifier = Modifier,
    label: String,
    value: Int,
    unit: String,
    onValueChange: (Int) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { if (value > 5) onValueChange(value - 5) }) {
                Icon(
                    painter = painterResource(UiRes.drawable.remove_24px),
                    contentDescription = "Decrease $label"
                )
            }
            Text("$value $unit", style = MaterialTheme.typography.bodyLarge)
            IconButton(onClick = { onValueChange(value + 5) }) {
                Icon(
                    painter = painterResource(UiRes.drawable.add_24px),
                    contentDescription = "Increase $label"
                )
            }
        }
    }
}