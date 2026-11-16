package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.yehorsk.medical_platform_mobile.core.domain.model.DaySchedule
import com.yehorsk.medical_platform_mobile.core.domain.model.WeekDay
import com.yehorsk.medical_platform_mobile.core.ui.components.buttons.DefaultAssistChip
import com.yehorsk.medical_platform_mobile.core.ui.components.buttons.TabToggleButton
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentPatient
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentStatus
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.viewmodel.CalendarMode
import com.yehorsk.medical_platform_mobile.util.formatDate
import com.yehorsk.medical_platform_mobile.util.formatMonth
import com.yehorsk.theme.AppTheme
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.arrow_back_24px
import medicalplatformmobile.shared.generated.resources.arrow_forward_24px
import medicalplatformmobile.shared.generated.resources.calendar_mode_day
import medicalplatformmobile.shared.generated.resources.calendar_mode_month
import medicalplatformmobile.shared.generated.resources.calendar_mode_week
import medicalplatformmobile.shared.generated.resources.calendar_next
import medicalplatformmobile.shared.generated.resources.calendar_previous
import medicalplatformmobile.shared.generated.resources.today
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import java.time.format.TextStyle
import java.util.Locale
import kotlin.time.Clock

// How far back and forward the doctor can go from today
private const val YEARS_RANGE = 1

/**
 * Desktop calendar for a doctor with Month, Week and Day views.
 * All three share [selectedDate], so switching views keeps the same date in view.
 *
 * @param headerActions extra buttons at the end of the header (e.g. refresh)
 */
@Composable
fun AppointmentsCalendar(
    modifier: Modifier = Modifier,
    mode: CalendarMode,
    selectedDate: LocalDate,
    appointments: List<Appointment>,
    daySchedules: List<DaySchedule>,
    onModeChange: (CalendarMode) -> Unit,
    onSelectedDateChange: (LocalDate) -> Unit,
    onDayClick: (LocalDate) -> Unit,
    onAppointmentClick: (String) -> Unit,
    headerActions: @Composable RowScope.() -> Unit = {},
) {
    val today = remember { Clock.System.todayIn(TimeZone.currentSystemDefault()) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
    val startDate = remember { today.minus(YEARS_RANGE, DateTimeUnit.YEAR) }
    val endDate = remember { today.plus(YEARS_RANGE, DateTimeUnit.YEAR) }

    val step = mode.step()
    val previousDate = selectedDate.minus(1, step)
    val nextDate = selectedDate.plus(1, step)

    ElevatedCard(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            CalendarHeader(
                modifier = Modifier.padding(bottom = 10.dp),
                title = calendarTitle(mode, selectedDate, firstDayOfWeek),
                isCurrentPeriod = isSamePeriod(mode, selectedDate, today, firstDayOfWeek),
                canGoBack = mode.isInRange(previousDate, startDate, endDate),
                canGoForward = mode.isInRange(nextDate, startDate, endDate),
                mode = mode,
                onPreviousClicked = { onSelectedDateChange(previousDate) },
                onNextClicked = { onSelectedDateChange(nextDate) },
                onTodayClicked = { onSelectedDateChange(today) },
                onModeChange = onModeChange,
                actions = headerActions
            )
            when (mode) {
                CalendarMode.MONTH -> CalendarMonthView(
                    selectedDate = selectedDate,
                    today = today,
                    startDate = startDate,
                    endDate = endDate,
                    firstDayOfWeek = firstDayOfWeek,
                    appointments = appointments,
                    daySchedules = daySchedules,
                    onSelectedDateChange = onSelectedDateChange,
                    onDayClick = onDayClick,
                    onAppointmentClick = onAppointmentClick
                )
                CalendarMode.WEEK -> CalendarWeekView(
                    selectedDate = selectedDate,
                    today = today,
                    startDate = startDate,
                    endDate = endDate,
                    firstDayOfWeek = firstDayOfWeek,
                    appointments = appointments,
                    daySchedules = daySchedules,
                    onSelectedDateChange = onSelectedDateChange,
                    onAppointmentClick = onAppointmentClick
                )
                CalendarMode.DAY -> CalendarDayView(
                    selectedDate = selectedDate,
                    today = today,
                    appointments = appointments,
                    daySchedules = daySchedules,
                    onAppointmentClick = onAppointmentClick
                )
            }
        }
    }
}

@Composable
private fun CalendarHeader(
    modifier: Modifier = Modifier,
    title: String,
    isCurrentPeriod: Boolean,
    canGoBack: Boolean,
    canGoForward: Boolean,
    mode: CalendarMode,
    onPreviousClicked: () -> Unit,
    onNextClicked: () -> Unit,
    onTodayClicked: () -> Unit,
    onModeChange: (CalendarMode) -> Unit,
    actions: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DefaultAssistChip(
            label = stringResource(UiRes.string.today),
            isSelected = isCurrentPeriod,
            onClick = onTodayClicked,
            textStyle = MaterialTheme.typography.labelMedium,
            height = 32.dp
        )
        Spacer(Modifier.width(8.dp))
        IconButton(
            enabled = canGoBack,
            onClick = onPreviousClicked
        ) {
            Icon(
                painter = painterResource(UiRes.drawable.arrow_back_24px),
                contentDescription = stringResource(UiRes.string.calendar_previous)
            )
        }
        IconButton(
            enabled = canGoForward,
            onClick = onNextClicked
        ) {
            Icon(
                painter = painterResource(UiRes.drawable.arrow_forward_24px),
                contentDescription = stringResource(UiRes.string.calendar_next)
            )
        }
        Spacer(Modifier.width(8.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        CalendarModeTabs(
            modifier = Modifier.width(300.dp),
            selectedMode = mode,
            onModeSelected = onModeChange
        )
        actions()
    }
}

/** Same pill as ViewAnatomyTabs / AppointmentsViewTabs. */
@Composable
private fun CalendarModeTabs(
    modifier: Modifier = Modifier,
    selectedMode: CalendarMode,
    onModeSelected: (CalendarMode) -> Unit
) {
    Box(
        modifier = modifier
            .background(Color(0xFFF0F0F0), RoundedCornerShape(50.dp))
            .padding(4.dp)
    ) {
        Row {
            CalendarMode.entries.forEach { mode ->
                TabToggleButton(
                    label = stringResource(mode.label()),
                    selected = selectedMode == mode,
                    onClick = { onModeSelected(mode) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

private fun CalendarMode.label() = when (this) {
    CalendarMode.MONTH -> UiRes.string.calendar_mode_month
    CalendarMode.WEEK -> UiRes.string.calendar_mode_week
    CalendarMode.DAY -> UiRes.string.calendar_mode_day
}

private fun CalendarMode.step(): DateTimeUnit.DateBased = when (this) {
    CalendarMode.MONTH -> DateTimeUnit.MONTH
    CalendarMode.WEEK -> DateTimeUnit.WEEK
    CalendarMode.DAY -> DateTimeUnit.DAY
}

// Months are compared as a whole, so the first/last month of the range stays reachable
private fun CalendarMode.isInRange(date: LocalDate, start: LocalDate, end: LocalDate): Boolean = when (this) {
    CalendarMode.MONTH -> date.yearMonth() in start.yearMonth()..end.yearMonth()
    else -> date in start..end
}

/** The seven dates of the week [date] falls in, starting at [firstDayOfWeek]. */
internal fun weekDaysOf(date: LocalDate, firstDayOfWeek: DayOfWeek): List<LocalDate> {
    val offset = (date.dayOfWeek.isoDayNumber - firstDayOfWeek.isoDayNumber + 7) % 7
    val start = date.minus(offset, DateTimeUnit.DAY)
    return (0 until 7).map { start.plus(it, DateTimeUnit.DAY) }
}

private fun isSamePeriod(mode: CalendarMode, date: LocalDate, today: LocalDate, firstDayOfWeek: DayOfWeek) =
    when (mode) {
        CalendarMode.MONTH -> date.yearMonth() == today.yearMonth()
        CalendarMode.WEEK -> today in weekDaysOf(date, firstDayOfWeek)
        CalendarMode.DAY -> date == today
    }

private fun calendarTitle(mode: CalendarMode, date: LocalDate, firstDayOfWeek: DayOfWeek): String =
    when (mode) {
        CalendarMode.MONTH -> formatMonth(date.yearMonth().toString())
        CalendarMode.WEEK -> weekDaysOf(date, firstDayOfWeek).let { weekRangeLabel(it.first(), it.last()) }
        CalendarMode.DAY -> "${formatDate(date.toString())}, ${date.year}"
    }

private fun weekRangeLabel(start: LocalDate, end: LocalDate): String {
    fun monthName(date: LocalDate) =
        java.time.Month.valueOf(date.month.name).getDisplayName(TextStyle.SHORT, Locale.getDefault())
    return when {
        start.year != end.year ->
            "${start.day} ${monthName(start)} ${start.year} – ${end.day} ${monthName(end)} ${end.year}"
        start.month != end.month ->
            "${start.day} ${monthName(start)} – ${end.day} ${monthName(end)} ${end.year}"
        else -> "${start.day} – ${end.day} ${monthName(end)} ${end.year}"
    }
}

@Preview(widthDp = 1200, heightDp = 760)
@Composable
private fun AppointmentsCalendarPreview() {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val weekStart = weekDaysOf(today, DayOfWeek.MONDAY).first()
    val schedules = listOf(WeekDay.MONDAY, WeekDay.TUESDAY, WeekDay.WEDNESDAY, WeekDay.THURSDAY, WeekDay.FRIDAY)
        .map { day ->
            DaySchedule(
                weekday = day, startTime = "08:00", endTime = "16:00",
                lunchStart = "12:00", lunchEnd = "12:30", isWorkingDay = true,
                slotDurationMinutes = 30, breakBetweenMinutes = 0
            )
        }
    val patient = AppointmentPatient(id = "p", firstName = "Jana", lastName = "Nováková", title = "")
    val appointments = listOf(
        Triple(0, LocalTime(8, 30), AppointmentStatus.CONFIRMED),
        Triple(0, LocalTime(9, 30), AppointmentStatus.CONFIRMED),
        Triple(1, LocalTime(10, 0), AppointmentStatus.PENDING),
        Triple(1, LocalTime(10, 0), AppointmentStatus.CANCELLED),
        Triple(3, LocalTime(14, 30), AppointmentStatus.COMPLETED),
    ).mapIndexed { i, (offset, time, status) ->
        Appointment(
            id = "$i", patient = patient, status = status, note = "Follow-up visit",
            date = weekStart.plus(offset, DateTimeUnit.DAY), time = time,
            createdAt = Clock.System.now(), updatedAt = Clock.System.now()
        )
    }
    var mode by remember { mutableStateOf(CalendarMode.WEEK) }
    var selectedDate by remember { mutableStateOf(today) }
    AppTheme {
        Surface {
            AppointmentsCalendar(
                mode = mode,
                selectedDate = selectedDate,
                appointments = appointments,
                daySchedules = schedules,
                onModeChange = { mode = it },
                onSelectedDateChange = { selectedDate = it },
                onDayClick = { selectedDate = it; mode = CalendarMode.DAY },
                onAppointmentClick = {}
            )
        }
    }
}
