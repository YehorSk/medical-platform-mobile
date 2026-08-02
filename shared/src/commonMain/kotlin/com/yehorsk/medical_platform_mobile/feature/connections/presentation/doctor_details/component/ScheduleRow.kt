package com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.domain.model.Schedule
import com.yehorsk.medical_platform_mobile.util.shortName
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.day_off
import org.jetbrains.compose.resources.stringResource


@Composable
fun ScheduleRow(
    schedule: Schedule
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier.width(48.dp),
            text = schedule.weekday.shortName(),
            style = MaterialTheme.typography.labelLarge,
            color = if (schedule.isWorkingDay)
                MaterialTheme.colorScheme.onSurface
            else
                MaterialTheme.colorScheme.outline
        )

        if (!schedule.isWorkingDay) {

            ScheduleChip(
                text = stringResource(UiRes.string.day_off),
                background = MaterialTheme.colorScheme.surfaceContainerHighest,
                content = MaterialTheme.colorScheme.onSurfaceVariant
            )

        } else {

            ScheduleChip(
                text = "${schedule.startTime} - ${schedule.endTime}",
                background = MaterialTheme.colorScheme.primaryContainer,
                content = MaterialTheme.colorScheme.onPrimaryContainer
            )

            if (schedule.lunchStart != null && schedule.lunchEnd != null) {

                Spacer(Modifier.width(8.dp))

                ScheduleChip(
                    text = "Break ${schedule.lunchStart}-${schedule.lunchEnd}",
                    background = MaterialTheme.colorScheme.tertiaryContainer,
                    content = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
    }
}