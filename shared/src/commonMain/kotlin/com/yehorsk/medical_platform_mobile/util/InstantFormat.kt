package com.yehorsk.medical_platform_mobile.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

private val shortDateTimeFormat = LocalDateTime.Format {
    day(); char('.'); monthNumber(); char('.'); year()
    chars(", ")
    hour(); char(':'); minute()
}

private val fullDateTimeFormat = LocalDateTime.Format {
    day(); char('.'); monthNumber(); char('.'); year()
    chars(", ")
    hour(); char(':'); minute(); char(':'); second()
}

fun Instant.toLocalShortDisplay(): String =
    toLocalDateTime(TimeZone.currentSystemDefault()).format(shortDateTimeFormat)

/** "17.11.2026, 10:41:12" in the device's time zone */
fun Instant.toLocalFullDisplay(): String =
    toLocalDateTime(TimeZone.currentSystemDefault()).format(fullDateTimeFormat)
