package com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.domain.model.DaySchedule
import com.yehorsk.medical_platform_mobile.util.shortName
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.day_off
import org.jetbrains.compose.resources.stringResource


@Composable
fun ScheduleRow(
    daySchedule: DaySchedule
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier.width(48.dp),
            text = daySchedule.weekday.shortName(),
            style = MaterialTheme.typography.labelLarge,
            color = if (daySchedule.isWorkingDay)
                MaterialTheme.colorScheme.onSurface
            else
                MaterialTheme.colorScheme.outline
        )

        if (!daySchedule.isWorkingDay) {

            ScheduleChip(
                text = stringResource(UiRes.string.day_off),
                background = MaterialTheme.colorScheme.surfaceContainerHighest,
                content = MaterialTheme.colorScheme.onSurfaceVariant
            )

        } else {

            ScheduleChip(
                text = "${daySchedule.startTime} - ${daySchedule.endTime}",
                background = MaterialTheme.colorScheme.primaryContainer,
                content = MaterialTheme.colorScheme.onPrimaryContainer
            )

            if (daySchedule.lunchStart != null && daySchedule.lunchEnd != null) {

                Spacer(Modifier.width(8.dp))

                ScheduleChip(
                    text = "Break ${daySchedule.lunchStart}-${daySchedule.lunchEnd}",
                    background = MaterialTheme.colorScheme.tertiaryContainer,
                    content = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
    }
}