package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.data.mappers.toDayOfWeek
import com.yehorsk.medical_platform_mobile.core.domain.model.DaySchedule
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentStatus
import com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.components.StatusBox
import com.yehorsk.medical_platform_mobile.util.localizedName
import com.yehorsk.medical_platform_mobile.util.toColor
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.calendar_no_patient
import org.jetbrains.compose.resources.stringResource

internal val HourHeight = 120.dp
// Below this a block has no room for two lines, so time and name share one
private val CompactBlockHeight = 52.dp
internal val TimeColumnWidth = 56.dp
private const val DEFAULT_START_HOUR = 8
private const val DEFAULT_END_HOUR = 18
private const val DEFAULT_SLOT_MINUTES = 30

/**
 * Hours down the side, one column per day. Used by the week view (7 days) and the day view (1 day).
 * Working hours, lunch and slot length come from the doctor's schedule;
 * appointments have no end time, so each block is one slot long.
 *
 * @param detailed wider blocks with the note and status, for the day view
 */
@Composable
internal fun HourGrid(
    modifier: Modifier = Modifier,
    days: List<LocalDate>,
    today: LocalDate,
    appointments: List<Appointment>,
    daySchedules: List<DaySchedule>,
    detailed: Boolean,
    onAppointmentClick: (String) -> Unit,
) {
    val scheduleByDay = remember(daySchedules) { daySchedules.associateBy { it.weekday.toDayOfWeek() } }
    val appointmentsByDate = remember(appointments, days) {
        appointments.filter { it.date in days }.groupBy { it.date }
    }
    val hourRange = remember(daySchedules, appointmentsByDate) {
        visibleHourRange(daySchedules, appointmentsByDate.values.flatten())
    }
    val totalHours = hourRange.last - hourRange.first

    Row(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        HourLabels(startHour = hourRange.first, endHour = hourRange.last)
        days.forEach { day ->
            DayColumn(
                modifier = Modifier
                    .weight(1f)
                    .height(HourHeight * totalHours),
                schedule = scheduleByDay[day.dayOfWeek],
                appointments = appointmentsByDate[day].orEmpty(),
                startHour = hourRange.first,
                totalHours = totalHours,
                isToday = day == today,
                detailed = detailed,
                onAppointmentClick = onAppointmentClick
            )
        }
    }
}

/** Day name above a date badge; today gets the same filled badge as a selected day in AppointmentCalendar. */
@Composable
internal fun DayTitle(
    modifier: Modifier = Modifier,
    date: LocalDate,
    isToday: Boolean
) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = date.dayOfWeek.localizedName(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelMedium,
            color = if (isToday) colors.primary else colors.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = if (isToday) colors.primary else Color.Transparent,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = date.day.toString(),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                color = if (isToday) colors.onPrimary else colors.onSurface
            )
        }
    }
}

@Composable
private fun HourLabels(startHour: Int, endHour: Int) {
    Box(
        modifier = Modifier
            .width(TimeColumnWidth)
            .height(HourHeight * (endHour - startHour))
    ) {
        for (hour in startHour until endHour) {
            Text(
                text = "${hour.toString().padStart(2, '0')}:00",
                modifier = Modifier
                    .offset(y = HourHeight * (hour - startHour))
                    .padding(start = 8.dp, top = 2.dp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun DayColumn(
    modifier: Modifier,
    schedule: DaySchedule?,
    appointments: List<Appointment>,
    startHour: Int,
    totalHours: Int,
    isToday: Boolean,
    detailed: Boolean,
    onAppointmentClick: (String) -> Unit,
) {
    val gridColor = MaterialTheme.colorScheme.outlineVariant
    val offHoursColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
    val todayColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.04f)
    val working = schedule?.takeIf { it.isWorkingDay }
    val workStart = working?.startTime?.toMinutes()
    val workEnd = working?.endTime?.toMinutes()
    val lunchStart = working?.lunchStart?.toMinutes()
    val lunchEnd = working?.lunchEnd?.toMinutes()
    val slotMinutes = schedule.slotMinutes()

    BoxWithConstraints(
        modifier = modifier.drawBehind {
            val hourPx = HourHeight.toPx()
            fun y(minutes: Int) = (minutes - startHour * 60) / 60f * hourPx

            if (isToday) drawRect(todayColor)
            // Shade time the doctor is not available
            if (workStart == null || workEnd == null) {
                drawRect(offHoursColor)
            } else {
                band(0f, y(workStart), offHoursColor)
                band(y(workEnd), size.height, offHoursColor)
                if (lunchStart != null && lunchEnd != null) band(y(lunchStart), y(lunchEnd), offHoursColor)
            }
            val stroke = 1.dp.toPx()
            for (hour in 0..totalHours) {
                drawLine(gridColor, Offset(0f, hour * hourPx), Offset(size.width, hour * hourPx), stroke)
            }
            drawLine(gridColor, Offset(0f, 0f), Offset(0f, size.height), stroke)
        }
    ) {
        // A cancelled slot can be booked again, so blocks starting at the same time share the width
        val blockHeight = HourHeight * (slotMinutes / 60f)
        appointments.groupBy { it.time }.forEach { (time, group) ->
            val blockWidth = maxWidth / group.size
            val top = HourHeight * ((time.toMinutes() - startHour * 60) / 60f)
            group.sortedBy { it.status.isInactive() }.forEachIndexed { index, appointment ->
                AppointmentBlock(
                    appointment = appointment,
                    compact = blockHeight < CompactBlockHeight,
                    detailed = detailed,
                    modifier = Modifier
                        .offset(x = blockWidth * index, y = top)
                        .width(blockWidth)
                        .height(blockHeight)
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    onClick = { onAppointmentClick(appointment.id) }
                )
            }
        }
    }
}

@Composable
private fun AppointmentBlock(
    appointment: Appointment,
    compact: Boolean,
    detailed: Boolean,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    val color = appointment.status.toColor()
    val inactive = appointment.status.isInactive()
    val name = appointment.patientName()
    val time = appointment.time.toDisplay()

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color.copy(alpha = if (inactive) 0.08f else 0.2f))
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(color)
        )
        when {
            compact -> Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BlockTime(time)
                Spacer(Modifier.width(6.dp))
                BlockName(name = name, inactive = inactive, maxLines = 1)
            }
            detailed -> Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        BlockTime(time)
                        Spacer(Modifier.width(8.dp))
                        BlockName(name = name, inactive = inactive, maxLines = 1)
                    }
                    if (appointment.note.isNotBlank()) {
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = appointment.note,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Spacer(Modifier.width(8.dp))
                StatusBox(status = appointment.status)
            }
            else -> Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)) {
                BlockTime(time)
                Spacer(Modifier.height(2.dp))
                BlockName(name = name, inactive = inactive, maxLines = 2)
            }
        }
    }
}

@Composable
private fun BlockTime(time: String) {
    Text(
        text = time,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 1
    )
}

@Composable
private fun BlockName(name: String, inactive: Boolean, maxLines: Int) {
    Text(
        text = name,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface,
        textDecoration = if (inactive) TextDecoration.LineThrough else null,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
internal fun Appointment.patientName(): String =
    patient?.fullName ?: stringResource(UiRes.string.calendar_no_patient)

private fun DrawScope.band(fromY: Float, toY: Float, color: Color) {
    val top = fromY.coerceIn(0f, size.height)
    val bottom = toY.coerceIn(0f, size.height)
    if (bottom > top) drawRect(color, topLeft = Offset(0f, top), size = Size(size.width, bottom - top))
}

internal fun AppointmentStatus.isInactive() =
    this == AppointmentStatus.CANCELLED || this == AppointmentStatus.REJECTED

private fun LocalTime.toMinutes() = hour * 60 + minute

internal fun LocalTime.toDisplay() =
    "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"

// Schedule times arrive as "HH:mm"
private fun String.toMinutes(): Int? = runCatching { LocalTime.parse(this).toMinutes() }.getOrNull()

private fun DaySchedule?.slotMinutes(): Int =
    this?.slotDurationMinutes?.takeIf { it > 0 } ?: DEFAULT_SLOT_MINUTES

/**
 * Hours shown in the grid: from the earliest working start to the latest working end,
 * widened so appointments outside working hours are still visible.
 */
private fun visibleHourRange(daySchedules: List<DaySchedule>, appointments: List<Appointment>): IntRange {
    val scheduleByDay = daySchedules.associateBy { it.weekday.toDayOfWeek() }
    val working = daySchedules.filter { it.isWorkingDay }
    val starts = working.mapNotNull { it.startTime.toMinutes() } +
        appointments.map { it.time.toMinutes() }
    val ends = working.mapNotNull { it.endTime.toMinutes() } +
        appointments.map { it.time.toMinutes() + scheduleByDay[it.date.dayOfWeek].slotMinutes() }

    val startHour = starts.minOrNull()?.div(60) ?: DEFAULT_START_HOUR
    val endHour = ends.maxOrNull()?.let { (it + 59) / 60 } ?: DEFAULT_END_HOUR
    val start = startHour.coerceIn(0, 23)
    return start..endHour.coerceIn(start + 1, 24)
}
