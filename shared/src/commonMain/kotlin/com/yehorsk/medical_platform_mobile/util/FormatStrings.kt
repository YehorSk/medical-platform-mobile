package com.yehorsk.medical_platform_mobile.util

import kotlinx.datetime.DayOfWeek
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.date_time_at_format
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

fun formatMonth(input: String): String {
    val parser = DateTimeFormatter.ofPattern("yyyy-MM")
    val yearMonth = YearMonth.parse(input, parser)
    val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
    return yearMonth.format(formatter)
}

fun formatTime(input: String): String {
    val parser = DateTimeFormatter.ofPattern("HH:mm:ss")
    val localDate = LocalTime.parse(input, parser)
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return localDate.format(formatter)
}

fun parseTime(input: String): LocalTime {
    val parser = DateTimeFormatter.ofPattern("HH:mm:ss")
    return LocalTime.parse(input, parser)
}

fun formatDate(input: String): String {
    val parser = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val localDate = LocalDate.parse(input, parser)
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d")
    return localDate.format(formatter)
}

fun formatDateTime(
    input: String,
    includeTime: Boolean = true,
    locale: Locale = Locale.getDefault()
): UiText {
    val value = LocalDateTime.parse(input)

    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d", locale)
    val date = value.format(dateFormatter)

    if (!includeTime) return UiText.DynamicString(date)

    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", locale)
    val time = value.format(timeFormatter)

    return UiText.Resource(
        id = UiRes.string.date_time_at_format,
        args = arrayOf(date, time)
    )
}

fun formatShortDateTime(input: String): String {
    val parser = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val localDate = LocalDate.parse(input, parser)
    val formatter = DateTimeFormatter.ofPattern("EEE, MMM d")
    return localDate.format(formatter)
}

fun DayOfWeek.localizedName(): String {
    return java.time.DayOfWeek.valueOf(name)
        .getDisplayName(
            TextStyle.SHORT,
            Locale.getDefault()
        )
}