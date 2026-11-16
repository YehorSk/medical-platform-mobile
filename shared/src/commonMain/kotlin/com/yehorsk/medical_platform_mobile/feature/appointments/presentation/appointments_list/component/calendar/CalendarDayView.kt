package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.component.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.domain.model.DaySchedule
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import kotlinx.datetime.LocalDate

/** A single wide day column; blocks have room for the note and status. */
@Composable
internal fun CalendarDayView(
    selectedDate: LocalDate,
    today: LocalDate,
    appointments: List<Appointment>,
    daySchedules: List<DaySchedule>,
    onAppointmentClick: (String) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Spacer(Modifier.width(TimeColumnWidth))
            DayTitle(
                modifier = Modifier.weight(1f),
                date = selectedDate,
                isToday = selectedDate == today
            )
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        HourGrid(
            days = listOf(selectedDate),
            today = today,
            appointments = appointments,
            daySchedules = daySchedules,
            detailed = true,
            onAppointmentClick = onAppointmentClick
        )
    }
}
