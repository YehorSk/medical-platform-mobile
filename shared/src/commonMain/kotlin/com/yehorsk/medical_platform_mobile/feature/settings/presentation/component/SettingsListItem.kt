package com.yehorsk.medical_platform_mobile.feature.settings.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.arrow_forward_24px
import org.jetbrains.compose.resources.painterResource

@Composable
fun SettingsListItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
){
    Row(
        modifier = modifier.background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ){
        Text(
            modifier = Modifier
                .padding(16.dp)
                .weight(2f),
            text = text,
            style = MaterialTheme.typography.bodyLarge,
        )
        Box(
            modifier = Modifier
                .padding(16.dp)
                .weight(1f),
            contentAlignment = Alignment.CenterEnd
        ){
            Icon(
                painter = painterResource(UiRes.drawable.arrow_forward_24px),
                contentDescription = ""
            )
        }
    }
    HorizontalDivider()
}