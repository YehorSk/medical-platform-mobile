package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.now
import com.kizitonwose.calendar.core.plusMonths
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.model.DayScheduleUi
import com.yehorsk.medical_platform_mobile.util.formatMonth
import com.yehorsk.medical_platform_mobile.util.localizedName
import com.yehorsk.theme.AppTheme
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.YearMonth
import kotlinx.datetime.format.*
import kotlinx.datetime.minusMonth
import kotlinx.datetime.plusMonth
import kotlinx.datetime.todayIn
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.arrow_back_24px
import medicalplatformmobile.shared.generated.resources.arrow_forward_24px
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalDateTime
import medicalplatformmobile.shared.generated.resources.available
import medicalplatformmobile.shared.generated.resources.selected
import medicalplatformmobile.shared.generated.resources.unavailable
import kotlin.time.Clock

private val CalendarMaxWidth = 360.dp

@Composable
fun AppointmentCalendar(
    modifier: Modifier = Modifier,
    selectedDate: String,
    weekSchedule: List<DayScheduleUi>,
    isLoading: Boolean,
    onUpdateSelectedDate: (String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val currentMonth = remember { YearMonth.now() }
    val startMonth = currentMonth
    val endMonth = remember { currentMonth.plusMonths(12) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
    val weekDays = remember(firstDayOfWeek) { daysOfWeek(firstDayOfWeek) }

    val selected = remember(selectedDate) {
        runCatching { LocalDate.parse(selectedDate) }.getOrNull()
    }

    val now = remember { Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) }
    val today = now.date
    val currentTime = now.time

    val scheduleByWeekday = remember(weekSchedule) { weekSchedule.associateBy { it.weekday } }

    val calendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    val visibleMonth = calendarState.firstVisibleMonth.yearMonth

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        ElevatedCard(
            modifier = Modifier
                .widthIn(max = CalendarMaxWidth)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    CalendarHeader(
                        modifier = Modifier.padding(bottom = 20.dp),
                        title = formatMonth(visibleMonth.toString()),
                        canGoBack = visibleMonth > startMonth,
                        canGoForward = visibleMonth < endMonth,
                        onPreviousClicked = {
                            coroutineScope.launch {
                                calendarState.animateScrollToMonth(visibleMonth.minusMonth())
                            }
                        },
                        onNextClicked = {
                            coroutineScope.launch {
                                calendarState.animateScrollToMonth(visibleMonth.plusMonth())
                            }
                        }
                    )

                    HorizontalCalendar(
                        state = calendarState,
                        monthHeader = { DaysOfWeekTitle(daysOfWeek = weekDays) },
                        dayContent = { day ->
                            Day(
                                day = day,
                                isSelected = day.date == selected,
                                isClosed = isDayClosed(
                                    date = day.date,
                                    schedule = scheduleByWeekday[day.date.dayOfWeek],
                                    today = today,
                                    currentTime = currentTime
                                ),
                                onClick = { clicked ->
                                    if (clicked.date != selected) {
                                        onUpdateSelectedDate(clicked.date.toString())
                                    }
                                }
                            )
                        }
                    )

                    CalendarLegend(
                        modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
                    )
                }
            }
        }
    }
}

private fun isDayClosed(
    date: LocalDate,
    schedule: DayScheduleUi?,
    today: LocalDate,
    currentTime: LocalTime
): Boolean = when {
    schedule == null || !schedule.isWorkingDay -> true
    date < today -> true
    date == today -> currentTime >= LocalTime.parse(schedule.endTime)
    else -> false
}

@Composable
private fun CalendarHeader(
    modifier: Modifier = Modifier,
    title: String,
    canGoBack: Boolean,
    canGoForward: Boolean,
    onPreviousClicked: () -> Unit,
    onNextClicked: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            enabled = canGoBack,
            onClick = onPreviousClicked
        ) {
            Icon(
                painter = painterResource(UiRes.drawable.arrow_back_24px),
                contentDescription = "Previous month"   // TODO: move to string resources
            )
        }
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        IconButton(
            enabled = canGoForward,
            onClick = onNextClicked
        ) {
            Icon(
                painter = painterResource(UiRes.drawable.arrow_forward_24px),
                contentDescription = "Next month"       // TODO: move to string resources
            )
        }
    }
}

@Composable
fun DaysOfWeekTitle(
    daysOfWeek: List<DayOfWeek>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                text = dayOfWeek.localizedName(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    isClosed: Boolean,
    onClick: (CalendarDay) -> Unit
) {
    // Leading/trailing days from neighbouring months: keep the grid spacing, draw nothing.
    if (day.position != DayPosition.MonthDate) {
        Box(modifier = Modifier.aspectRatio(1f))
        return
    }

    val colors = MaterialTheme.colorScheme
    val shape = RoundedCornerShape(10.dp)

    val backgroundColor = when {
        isSelected -> colors.primary
        isClosed -> colors.surfaceVariant
        else -> colors.surface
    }
    val contentColor = when {
        isSelected -> colors.onPrimary
        isClosed -> colors.onSurfaceVariant.copy(alpha = 0.5f)
        else -> colors.onSurface
    }
    val borderColor = if (isSelected || isClosed) Color.Transparent else colors.outlineVariant

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(1.dp)
            .clip(shape)
            .background(backgroundColor)
            .border(width = 2.dp, color = borderColor, shape = shape)
            .clickable(
                enabled = !isClosed,
                onClick = { onClick(day) }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.day.toString(),
            color = contentColor
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CalendarLegend(modifier: Modifier = Modifier) {
    val colors = MaterialTheme.colorScheme

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        LegendItem(
            text = stringResource(UiRes.string.available),
            color = colors.surface,
            borderColor = colors.outlineVariant
        )
        LegendItem(
            text = stringResource(UiRes.string.selected),
            color = colors.primary
        )
        LegendItem(
            text = stringResource(UiRes.string.unavailable),
            color = colors.surfaceVariant
        )
    }
}

@Composable
private fun LegendItem(
    text: String,
    color: Color,
    borderColor: Color = Color.Transparent
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, CircleShape)
                .border(1.dp, borderColor, CircleShape)
        )
        Spacer(Modifier.width(6.dp))
        Text(text, style = MaterialTheme.typography.labelSmall)
    }
}

@Preview
@Composable
fun CalendarPreview(){
    AppTheme {
        val weekPreview = DayOfWeek.entries.map { day ->
            val isWorkingDay = day !in setOf(
                DayOfWeek.SATURDAY,
                DayOfWeek.SUNDAY
            )

            DayScheduleUi(
                weekday = day,
                isWorkingDay = isWorkingDay,
                startTime = "09:00:00",
                endTime = "18:00:00",
            )
        }
        AppointmentCalendar(
            onUpdateSelectedDate = {},
            selectedDate = "2026-08-04",
            weekSchedule = weekPreview,
            isLoading = false
        )
    }
}