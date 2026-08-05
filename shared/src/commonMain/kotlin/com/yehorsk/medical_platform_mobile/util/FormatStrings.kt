package com.yehorsk.medical_platform_mobile.util

import kotlinx.datetime.DayOfWeek
import java.time.LocalDate
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

fun formatDateTime(input: String): String {
    val parser = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val localDate = LocalDate.parse(input, parser)
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d")
    return localDate.format(formatter)
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