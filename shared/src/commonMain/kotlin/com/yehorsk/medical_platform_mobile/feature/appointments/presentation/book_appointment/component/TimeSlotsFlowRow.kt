package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.theme.AppTheme
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TimeSlotsFlowRow(
    times: List<String>,
    selectedTime: String?,
    modifier: Modifier = Modifier,
    onTimeSelected: (String) -> Unit
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
    ){
        FlowRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            times.forEach { time ->
                TimeSlotItem(
                    time = time,
                    selected = time == selectedTime,
                    onClick = { onTimeSelected(time) }
                )
            }
        }
    }
}

@Composable
private fun TimeSlotItem(
    time: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = modifier.height(48.dp),
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = if (selected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            1.dp,
            if (selected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Box(
            modifier = Modifier
                .padding(15.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = time,
                style = MaterialTheme.typography.titleSmall,
                color = if (selected)
                    MaterialTheme.colorScheme.onPrimaryContainer
                else
                    MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun TimeSlotsFlowRowPreview() {

    val times = listOf(
        "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30"
    )

    AppTheme {
        TimeSlotsFlowRow(
            times = times,
            selectedTime = "10:00",
            onTimeSelected = {

            }
        )
    }
}