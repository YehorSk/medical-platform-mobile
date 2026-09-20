package com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.util.UiText

@Composable
fun CreateRecordScreenComponent(
    modifier: Modifier = Modifier,
    title: UiText,
    content: @Composable () -> Unit = {},

){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title.asString(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        content()
    }

}