package com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.domain.model.AppointmentStatus
import com.yehorsk.medical_platform_mobile.util.toColor
import com.yehorsk.theme.AppTheme
import java.util.Locale
import java.util.Locale.getDefault
import androidx.compose.ui.platform.LocalLocale

@Composable
fun StatusBox(
    modifier: Modifier = Modifier,
    status: AppointmentStatus
){
    Box(
        modifier = modifier
            .background(
                color = status.toColor(),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(
                vertical = 5.dp,
                horizontal = 8.dp
            )
    ){
        Text(
            text = status.name.lowercase(LocalLocale.current.platformLocale).replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Preview
@Composable
fun StatusBoxPreview(){
    AppTheme {
        Column {
            AppointmentStatus.entries.toTypedArray().forEach { status ->
                StatusBox(
                    status = status
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}