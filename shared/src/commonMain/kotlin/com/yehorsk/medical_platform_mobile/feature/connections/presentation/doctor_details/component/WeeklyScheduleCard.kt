package com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.yehorsk.medical_platform_mobile.core.domain.model.DaySchedule
import com.yehorsk.medical_platform_mobile.core.domain.model.WeekDay
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultButton
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.book_appointment
import medicalplatformmobile.shared.generated.resources.calendar_today_24px
import medicalplatformmobile.shared.generated.resources.weekly_schedule
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun WeeklyScheduleCard(
    modifier: Modifier = Modifier,
    daySchedule: List<DaySchedule>,
    onBookClicked: () -> Unit= {}
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(UiRes.drawable.calendar_today_24px),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    text = stringResource(UiRes.string.weekly_schedule),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(16.dp))

            daySchedule.forEach { day ->
                ScheduleRow(day)
                Spacer(Modifier.height(8.dp))
            }

            Spacer(Modifier.height(12.dp))

            DefaultButton(
                modifier = Modifier
                    .padding(vertical = 12.dp),
                onClick = { onBookClicked() },
                text = stringResource(UiRes.string.book_appointment)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeeklyScheduleCardPreview() {

    AppTheme {
        WeeklyScheduleCard(
            modifier = Modifier.padding(16.dp),
            daySchedule = listOf(
                DaySchedule(
                    WeekDay.MONDAY,
                    "09:00",
                    "17:00",
                    "13:00",
                    "14:00",
                    true,
                    30,
                    10
                ),
                DaySchedule(
                    WeekDay.TUESDAY,
                    "09:00",
                    "17:00",
                    "13:00",
                    "14:00",
                    true,
                    30,
                    10
                ),
                DaySchedule(
                    WeekDay.WEDNESDAY,
                    "10:00",
                    "18:00",
                    "13:00",
                    "14:00",
                    true,
                    30,
                    10
                ),
                DaySchedule(
                    WeekDay.THURSDAY,
                    "09:00",
                    "17:00",
                    null,
                    null,
                    true,
                    30,
                    10
                ),
                DaySchedule(
                    WeekDay.FRIDAY,
                    "09:00",
                    "15:00",
                    null,
                    null,
                    true,
                    30,
                    10
                ),
                DaySchedule(
                    WeekDay.SATURDAY,
                    "",
                    "",
                    null,
                    null,
                    false,
                    30,
                    10
                ),
                DaySchedule(
                    WeekDay.SUNDAY,
                    "",
                    "",
                    null,
                    null,
                    false,
                    30,
                    10
                )
            )
        )
    }
}