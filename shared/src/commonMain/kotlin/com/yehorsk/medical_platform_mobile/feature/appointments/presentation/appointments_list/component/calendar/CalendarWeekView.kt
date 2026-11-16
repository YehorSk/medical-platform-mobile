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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.yehorsk.medical_platform_mobile.core.domain.model.DaySchedule
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

/**
 * Seven day columns. The day header is kizitonwose's WeekCalendar, so weeks can be swiped.
 * The shared [selectedDate] and the calendar's visible week are kept in sync both ways.
 */
@Composable
internal fun CalendarWeekView(
    selectedDate: LocalDate,
    today: LocalDate,
    startDate: LocalDate,
    endDate: LocalDate,
    firstDayOfWeek: DayOfWeek,
    appointments: List<Appointment>,
    daySchedules: List<DaySchedule>,
    onSelectedDateChange: (LocalDate) -> Unit,
    onAppointmentClick: (String) -> Unit,
) {
    // The arguments are rememberSaveable keys: passing the live selectedDate would recreate the
    // state on every change, so only the date the view opened with is used. Syncing is done below.
    val initialDate = remember { selectedDate }
    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = initialDate,
        firstDayOfWeek = firstDayOfWeek
    )
    val currentSelectedDate by rememberUpdatedState(selectedDate)
    val currentOnSelectedDateChange by rememberUpdatedState(onSelectedDateChange)

    // Arrows / Today changed the date: bring its week into view
    LaunchedEffect(selectedDate) {
        if (state.firstVisibleWeek.days.none { it.date == selectedDate }) {
            state.animateScrollToWeek(selectedDate)
        }
    }
    // The user swiped to another week: move the date there, keeping the same weekday.
    // Waits for the scroll to settle, so the grid doesn't jump while dragging.
    LaunchedEffect(state) {
        snapshotFlow { state.isScrollInProgress to state.firstVisibleWeek }
            .filter { (isScrolling, _) -> !isScrolling }
            .map { (_, week) -> week.days.map { it.date } }
            .distinctUntilChanged()
            .collect { visibleDays ->
                val selected = currentSelectedDate
                if (selected !in visibleDays) {
                    currentOnSelectedDateChange(
                        visibleDays.firstOrNull { it.dayOfWeek == selected.dayOfWeek } ?: visibleDays.first()
                    )
                }
            }
    }

    val days = remember(selectedDate, firstDayOfWeek) { weekDaysOf(selectedDate, firstDayOfWeek) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Spacer(Modifier.width(TimeColumnWidth))
            WeekCalendar(
                modifier = Modifier.weight(1f),
                state = state,
                dayContent = { day ->
                    DayTitle(date = day.date, isToday = day.date == today)
                }
            )
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        HourGrid(
            days = days,
            today = today,
            appointments = appointments,
            daySchedules = daySchedules,
            detailed = false,
            onAppointmentClick = onAppointmentClick
        )
    }
}
