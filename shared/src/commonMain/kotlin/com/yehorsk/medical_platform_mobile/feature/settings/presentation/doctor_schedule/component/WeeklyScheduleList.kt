package com.yehorsk.medical_platform_mobile.feature.settings.presentation.doctor_schedule.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.util.currentWindowLayout
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.doctor_schedule.viewmodel.DayScheduleUiState
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.doctor_schedule.viewmodel.defaultWeekSchedule
import com.yehorsk.theme.AppTheme
import kotlin.collections.forEach

@Composable
fun WeeklyScheduleList(
    schedule: List<DayScheduleUiState>,
    onDayChange: (DayScheduleUiState) -> Unit,
    onCopyToWeekdays: (DayScheduleUiState) -> Unit,
    modifier: Modifier = Modifier,
) {
    val columns = if (currentWindowLayout().isExpanded) 2 else 1

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(schedule, key = { it.weekDay }) { day ->
            DayScheduleRow(day = day, onChange = onDayChange, onCopyToWeekdays = { onCopyToWeekdays(day) })
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeeklyScheduleListPreview() {
    AppTheme {
        Surface {
            WeeklyScheduleList(
                schedule = defaultWeekSchedule(),
                onDayChange = {},
                onCopyToWeekdays = {},
            )
        }
    }
}