package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.util.formatDateTime
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.calendar_today_24px
import org.jetbrains.compose.resources.painterResource

@Composable
fun SelectedDate(
    modifier: Modifier = Modifier,
    date: String
){
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 15.dp,
                    bottom = 15.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(UiRes.drawable.calendar_today_24px),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .padding(start = 15.dp),
                text = formatDateTime(date),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun SelectedDatePreview(){
    AppTheme {
        SelectedDate(
            date = "2026-08-05"
        )
    }
}