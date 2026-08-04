package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.yehorsk.medical_platform_mobile.util.formatMonth
import com.yehorsk.medical_platform_mobile.util.localizedName
import com.yehorsk.theme.AppTheme
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.YearMonth
import kotlinx.datetime.format.*
import kotlinx.datetime.minusMonth
import kotlinx.datetime.plusMonth
import kotlinx.datetime.todayIn
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.arrow_back_24px
import medicalplatformmobile.shared.generated.resources.arrow_forward_24px
import medicalplatformmobile.shared.generated.resources.date
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlinx.datetime.DayOfWeek
import medicalplatformmobile.shared.generated.resources.available
import medicalplatformmobile.shared.generated.resources.selected
import medicalplatformmobile.shared.generated.resources.unavailable
import kotlin.time.Clock


@Composable
fun AppointmentCalendar(
    modifier: Modifier = Modifier,
    onUpdateSelectedDate: (String) -> Unit,
    selectedDate: String,
    closedDays: Array<String>
){
    val coroutineScope = rememberCoroutineScope()
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth }
    val endMonth = remember { currentMonth.plusMonths(12) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }

    val formatter = LocalDate.Format {
        year()
        char('-')
        monthNumber()
        char('-')
        day()
    }
    var localDate = LocalDate.parse(selectedDate, formatter)

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 10.dp,
                    bottom = 10.dp
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            val previousMonth = state.firstVisibleMonth.yearMonth.minusMonth()
                            if (previousMonth >= startMonth) {
                                state.scrollToMonth(previousMonth)
                            }
                        }
                    }
                ) {
                    Icon(
                        modifier = Modifier.weight(1f),
                        painter = painterResource(UiRes.drawable.arrow_back_24px),
                        contentDescription = ""
                    )
                }
                Text(
                    modifier = Modifier
                        .weight(1f),
                    textAlign = TextAlign.Center,
                    text = formatMonth(state.lastVisibleMonth.yearMonth.toString()),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            val nextMonth = state.firstVisibleMonth.yearMonth.plusMonth()
                            if (nextMonth <= endMonth) {
                                state.scrollToMonth(nextMonth)
                            }
                        }
                    }
                ) {
                    Icon(
                        modifier = Modifier.weight(1f),
                        painter = painterResource(UiRes.drawable.arrow_forward_24px),
                        contentDescription = ""
                    )
                }
            }
            HorizontalCalendar(
                state = state,
                dayContent = { day ->
                    Day(
                        day,
                        isSelected = localDate == day.date,
                        onClick = { day ->
                            localDate = if (localDate == day.date) localDate else day.date
                            onUpdateSelectedDate(localDate.toString())
                        },
                        isClosed = false
                    )
                },
                monthHeader = {
                    DaysOfWeekTitle(
                        daysOfWeek = daysOfWeek(),
                    )
                }
            )
            Row(
                modifier = Modifier
                    .padding(
                        top = 20.dp,
                        bottom = 10.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                listOf(
                    stringResource(UiRes.string.available) to Color(0xFFE6F8F6),
                    stringResource(UiRes.string.selected) to MaterialTheme.colorScheme.primary,
                    stringResource(UiRes.string.unavailable) to MaterialTheme.colorScheme.surfaceVariant
                ).forEach { (text, color) ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier
                                .size(12.dp)
                                .background(color, CircleShape)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(text, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
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
            .padding(
                bottom = 10.dp
            )
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                text = dayOfWeek.localizedName(),
            )
        }
    }
}

@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: (CalendarDay) -> Unit,
    isClosed: Boolean
) {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

    val isPastDate = day.date < today
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(1.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = when{
                day.position != DayPosition.MonthDate -> Color.Transparent
                isSelected -> MaterialTheme.colorScheme.primary
                isPastDate -> Color.LightGray
                isClosed -> Color.LightGray
                else -> Color.White
            })
            .border(
                width = 2.dp,
                color = when{
                    day.position != DayPosition.MonthDate -> Color.Transparent
                    isSelected -> Color.Transparent
                    isPastDate -> Color.Transparent
                    isClosed -> Color.Transparent
                    else -> Color.LightGray
                },
                shape = RoundedCornerShape(10.dp))
            .clickable(
                enabled = day.position == DayPosition.MonthDate && !isPastDate && !isClosed,
                onClick = { onClick(day) }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = when {
                day.position != DayPosition.MonthDate -> Color.Transparent
                isPastDate -> Color.Gray
                isClosed -> Color.Gray
                isSelected -> Color.White
                else -> Color.Black
            }
        )
    }
}

@Preview
@Composable
fun CalendarPreview(){
    AppTheme {
        AppointmentCalendar(
            onUpdateSelectedDate = {},
            selectedDate = "2026-08-04",
            closedDays = arrayOf()
        )
    }
}