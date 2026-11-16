package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.ContentHeightMode
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.yehorsk.medical_platform_mobile.core.data.mappers.toDayOfWeek
import com.yehorsk.medical_platform_mobile.core.domain.model.DaySchedule
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.component.DaysOfWeekTitle
import com.yehorsk.medical_platform_mobile.util.toColor
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.calendar_more
import org.jetbrains.compose.resources.stringResource

private val DayNumberSize = 28.dp
private val ChipHeight = 20.dp
private val ChipSpacing = 2.dp

/**
 * Month grid built on kizitonwose's HorizontalCalendar (the same component as the booking calendar).
 * Each day lists its appointments as short chips; clicking a day opens it in the day view.
 */
@Composable
internal fun CalendarMonthView(
    selectedDate: LocalDate,
    today: LocalDate,
    startDate: LocalDate,
    endDate: LocalDate,
    firstDayOfWeek: DayOfWeek,
    appointments: List<Appointment>,
    daySchedules: List<DaySchedule>,
    onSelectedDateChange: (LocalDate) -> Unit,
    onDayClick: (LocalDate) -> Unit,
    onAppointmentClick: (String) -> Unit,
) {
    val selectedMonth = selectedDate.yearMonth()
    // The arguments are rememberSaveable keys, so only the month the view opened with is passed
    val initialMonth = remember { selectedMonth }
    val state = rememberCalendarState(
        startMonth = remember(startDate) { startDate.yearMonth() },
        endMonth = remember(endDate) { endDate.yearMonth() },
        firstVisibleMonth = initialMonth,
        firstDayOfWeek = firstDayOfWeek
    )
    val currentSelectedDate by rememberUpdatedState(selectedDate)
    val currentOnSelectedDateChange by rememberUpdatedState(onSelectedDateChange)

    // Arrows / Today changed the month: scroll to it
    LaunchedEffect(selectedMonth) {
        if (state.firstVisibleMonth.yearMonth != selectedMonth) {
            state.animateScrollToMonth(selectedMonth)
        }
    }
    // The user swiped to another month: move the date there, keeping the day of month where possible
    LaunchedEffect(state) {
        snapshotFlow { state.isScrollInProgress to state.firstVisibleMonth.yearMonth }
            .filter { (isScrolling, _) -> !isScrolling }
            .map { (_, month) -> month }
            .distinctUntilChanged()
            .collect { month ->
                val selected = currentSelectedDate
                if (selected.yearMonth() != month) {
                    currentOnSelectedDateChange(month.dateClamped(selected.day))
                }
            }
    }

    val appointmentsByDate = remember(appointments) {
        appointments.groupBy { it.date }.mapValues { (_, list) -> list.sortedBy { it.time } }
    }
    val scheduleByDay = remember(daySchedules) { daySchedules.associateBy { it.weekday.toDayOfWeek() } }
    val weekDays = remember(firstDayOfWeek) { daysOfWeek(firstDayOfWeek) }

    HorizontalCalendar(
        modifier = Modifier.fillMaxSize(),
        state = state,
        contentHeightMode = ContentHeightMode.Fill,
        monthHeader = { DaysOfWeekTitle(daysOfWeek = weekDays) },
        dayContent = { day ->
            // No schedule loaded yet: don't grey anything out
            val isWorkingDay = daySchedules.isEmpty() || scheduleByDay[day.date.dayOfWeek]?.isWorkingDay == true
            MonthDayCell(
                day = day,
                isToday = day.date == today,
                isWorkingDay = isWorkingDay,
                appointments = appointmentsByDate[day.date].orEmpty(),
                onClick = { onDayClick(day.date) },
                onAppointmentClick = onAppointmentClick
            )
        }
    )
}

@Composable
private fun MonthDayCell(
    day: CalendarDay,
    isToday: Boolean,
    isWorkingDay: Boolean,
    appointments: List<Appointment>,
    onClick: () -> Unit,
    onAppointmentClick: (String) -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    // Leading/trailing days from neighbouring months: keep the grid, show only a faded number
    val inMonth = day.position == DayPosition.MonthDate

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .clipToBounds()
            .border(0.5.dp, colors.outlineVariant)
            .background(
                if (inMonth && !isWorkingDay) colors.surfaceVariant.copy(alpha = 0.6f) else Color.Transparent
            )
            .clickable(enabled = inMonth, onClick = onClick)
            .padding(4.dp)
    ) {
        // How many chips fit under the day number; the last row turns into "+N more" if needed
        val rows = ((maxHeight - DayNumberSize - ChipSpacing) / (ChipHeight + ChipSpacing)).toInt().coerceAtLeast(0)
        val visible = if (appointments.size > rows) appointments.take((rows - 1).coerceAtLeast(0)) else appointments
        val hiddenCount = appointments.size - visible.size

        Column(verticalArrangement = Arrangement.spacedBy(ChipSpacing)) {
            Box(
                modifier = Modifier
                    .size(DayNumberSize)
                    .background(
                        color = if (isToday) colors.primary else Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day.date.day.toString(),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                    color = when {
                        isToday -> colors.onPrimary
                        inMonth -> colors.onSurface
                        else -> colors.onSurfaceVariant.copy(alpha = 0.5f)
                    }
                )
            }
            if (inMonth) {
                visible.forEach { appointment ->
                    MonthAppointmentChip(
                        appointment = appointment,
                        onClick = { onAppointmentClick(appointment.id) }
                    )
                }
                if (hiddenCount > 0) {
                    Text(
                        modifier = Modifier
                            .height(ChipHeight)
                            .padding(horizontal = 6.dp),
                        text = stringResource(UiRes.string.calendar_more, hiddenCount),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.primary,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
private fun MonthAppointmentChip(
    appointment: Appointment,
    onClick: () -> Unit,
) {
    val color = appointment.status.toColor()
    val inactive = appointment.status.isInactive()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(ChipHeight)
            .clip(RoundedCornerShape(6.dp))
            .background(color.copy(alpha = if (inactive) 0.08f else 0.2f))
            .clickable(onClick = onClick)
            .padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(color, RoundedCornerShape(3.dp))
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = "${appointment.time.toDisplay()} ${appointment.patientName()}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
            textDecoration = if (inactive) TextDecoration.LineThrough else null,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

internal fun LocalDate.yearMonth(): YearMonth = YearMonth(year, month)

/** The given day in this month, or the month's last day if it is shorter (31 Jan -> 28 Feb). */
private fun YearMonth.dateClamped(day: Int): LocalDate {
    val first = LocalDate(year, month, 1)
    val lastDay = first.plus(1, DateTimeUnit.MONTH).minus(1, DateTimeUnit.DAY).day
    return LocalDate(year, month, day.coerceAtMost(lastDay))
}
