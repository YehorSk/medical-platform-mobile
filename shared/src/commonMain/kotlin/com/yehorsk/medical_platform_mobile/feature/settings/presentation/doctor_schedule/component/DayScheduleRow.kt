package com.yehorsk.medical_platform_mobile.feature.settings.presentation.doctor_schedule.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.domain.model.WeekDay
import com.yehorsk.medical_platform_mobile.core.ui.components.textfields.DefaultStepperField
import com.yehorsk.medical_platform_mobile.core.ui.components.textfields.DefaultTimePicker
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.doctor_schedule.viewmodel.DayScheduleUiState
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.doctor_schedule.viewmodel.WEEKEND
import com.yehorsk.medical_platform_mobile.util.fullName
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.schedule_24px
import org.jetbrains.compose.resources.painterResource
import java.time.LocalTime

@Composable
fun DayScheduleRow(
    day: DayScheduleUiState,
    onChange: (DayScheduleUiState) -> Unit,
    onCopyToWeekdays: () -> Unit,
) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = day.weekDay.fullName(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                )
                Switch(
                    checked = day.isWorkingDay,
                    onCheckedChange = { onChange(day.copy(isWorkingDay = it)) },
                )
            }

            if (day.isWorkingDay) {
                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    DefaultTimePicker(
                        modifier = Modifier.weight(1f),
                        trailingIcon = painterResource(UiRes.drawable.schedule_24px),
                        trailingIconDescr = "Start time",
                        value = day.startTime ?: LocalTime.of(9, 0),
                        header = "Start time",
                        onValueChange = { onChange(day.copy(startTime = it)) },
                    )
                    DefaultTimePicker(
                        modifier = Modifier.weight(1f),
                        trailingIcon = painterResource(UiRes.drawable.schedule_24px),
                        trailingIconDescr = "Start time",
                        value = day.endTime ?: LocalTime.of(17, 0),
                        header = "End time",
                        onValueChange = { onChange(day.copy(endTime = it)) },
                    )
                }

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    DefaultStepperField(
                        modifier = Modifier.weight(1f),
                        label = "Slot length",
                        value = day.slotDurationMinutes,
                        unit = "min",
                        onValueChange = { onChange(day.copy(slotDurationMinutes = it)) },
                    )
                    DefaultStepperField(
                        modifier = Modifier.weight(1f),
                        label = "Break between slots",
                        value = day.breakBetweenMinutes,
                        unit = "min",
                        onValueChange = { onChange(day.copy(breakBetweenMinutes = it)) },
                    )
                }

                Spacer(Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = day.hasBreak,
                        onCheckedChange = { checked ->
                            onChange(
                                day.copy(
                                    hasBreak = checked,
                                    lunchStart = if (checked) day.lunchStart ?: LocalTime.of(13, 0) else null,
                                    lunchEnd = if (checked) day.lunchEnd ?: LocalTime.of(13, 30) else null,
                                )
                            )
                        },
                    )
                    Text("Add a break", style = MaterialTheme.typography.bodyMedium)
                }

                if (day.hasBreak) {
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        DefaultTimePicker(
                            modifier = Modifier.weight(1f),
                            value = day.lunchStart ?: LocalTime.of(13, 0),
                            header = "Break start",
                            onValueChange = { onChange(day.copy(lunchStart = it)) },
                        )
                        DefaultTimePicker(
                            modifier = Modifier.weight(1f),
                            value = day.lunchEnd ?: LocalTime.of(13, 30),
                            header = "Break end",
                            onValueChange = { onChange(day.copy(lunchEnd = it)) },
                        )
                    }
                }

                if (day.weekDay !in WEEKEND) {
                    Spacer(Modifier.height(8.dp))
                    TextButton(onClick = onCopyToWeekdays, modifier = Modifier.align(Alignment.End)) {
                        Text("Copy to weekdays")
                    }
                }
            }
        }
    }
}