package com.yehorsk.medical_platform_mobile.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.yehorsk.medical_platform_mobile.core.domain.model.AccessStatus
import com.yehorsk.medical_platform_mobile.core.domain.model.AppointmentStatus
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.domain.model.WeekDay
import com.yehorsk.theme.LocalExtendedColors
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlin.time.Clock
import kotlin.time.Instant
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.d_ago
import medicalplatformmobile.shared.generated.resources.friday_short
import medicalplatformmobile.shared.generated.resources.h_ago
import medicalplatformmobile.shared.generated.resources.just_now
import medicalplatformmobile.shared.generated.resources.m_ago
import medicalplatformmobile.shared.generated.resources.monday_short
import medicalplatformmobile.shared.generated.resources.saturday_short
import medicalplatformmobile.shared.generated.resources.sunday_short
import medicalplatformmobile.shared.generated.resources.thursday_short
import medicalplatformmobile.shared.generated.resources.tuesday_short
import medicalplatformmobile.shared.generated.resources.wednesday_short
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import java.time.LocalTime
import java.time.format.DateTimeFormatter


fun getRole(role: String): UserRole {
    return UserRole.entries.find { it.name.equals(role, ignoreCase = true) } ?: UserRole.PATIENT
}

fun getAccessStatus(status: String): AccessStatus {
    return AccessStatus.entries.find { it.name.equals(status, ignoreCase = true) } ?: AccessStatus.UNKNOWN
}

fun getWeekDay(status: String): WeekDay {
    return WeekDay.entries.find { it.name.equals(status, ignoreCase = true) } ?: WeekDay.UNKNOWN
}

private val apiFormatter = DateTimeFormatter.ISO_LOCAL_TIME
private val uiFormatter = DateTimeFormatter.ofPattern("HH:mm")

fun String.toDisplayTime(): String =
    LocalTime.parse(this, apiFormatter).format(uiFormatter)

@Composable
fun AppointmentStatus.toColor(): Color {
    val colors = LocalExtendedColors.current
    return when (this) {
        AppointmentStatus.PENDING -> colors.statusPending
        AppointmentStatus.CONFIRMED -> colors.statusConfirmed
        AppointmentStatus.REJECTED -> colors.statusRejected
        AppointmentStatus.CANCELLED -> colors.statusCancelled
        AppointmentStatus.COMPLETED -> colors.statusCompleted
    }
}

sealed class TimeAgo {
    data object JustNow : TimeAgo()
    data class Minutes(val value: Long) : TimeAgo()
    data class Hours(val value: Long) : TimeAgo()
    data class Days(val value: Long) : TimeAgo()
    data class Date(val value: String) : TimeAgo()
}

fun formatTimeAgo(createdAt: String): TimeAgo {
    val instant = Instant.parse(createdAt)
    val diff = Clock.System.now().toEpochMilliseconds() - instant.toEpochMilliseconds()

    val minutes = diff / 1000 / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        minutes < 1 -> TimeAgo.JustNow
        minutes < 60 -> TimeAgo.Minutes(minutes)
        hours < 24 -> TimeAgo.Hours(hours)
        days < 7 -> TimeAgo.Days(days)
        else -> {
            val dt = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            TimeAgo.Date("${dt.day}.${dt.month.number.toString().padStart(2, '0')}.${dt.year}")
        }
    }
}

@Composable
fun TimeAgo.toText(): String {
    return when (this) {
        is TimeAgo.JustNow -> stringResource(UiRes.string.just_now)
        is TimeAgo.Minutes -> stringResource(UiRes.string.m_ago, value)
        is TimeAgo.Hours -> stringResource(UiRes.string.h_ago, value)
        is TimeAgo.Days -> stringResource(UiRes.string.d_ago, value)
        is TimeAgo.Date -> value
    }
}

@Composable
fun WeekDay.shortName(): String = when (this) {
    WeekDay.MONDAY -> stringResource(UiRes.string.monday_short)
    WeekDay.TUESDAY -> stringResource(UiRes.string.tuesday_short)
    WeekDay.WEDNESDAY -> stringResource(UiRes.string.wednesday_short)
    WeekDay.THURSDAY -> stringResource(UiRes.string.thursday_short)
    WeekDay.FRIDAY -> stringResource(UiRes.string.friday_short)
    WeekDay.SATURDAY -> stringResource(UiRes.string.saturday_short)
    WeekDay.SUNDAY -> stringResource(UiRes.string.sunday_short)
    WeekDay.UNKNOWN -> "-"
}

fun extractDate(datetime: String): String {
    val instant = Instant.parse(datetime)
    val dt = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${dt.day.toString().padStart(2, '0')}.${dt.month.number.toString().padStart(2, '0')}.${dt.year}"
}

fun extractTime(datetime: String): String {
    val instant = Instant.parse(datetime)
    val dt = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${dt.hour.toString().padStart(2, '0')}:${dt.minute.toString().padStart(2, '0')}"
}