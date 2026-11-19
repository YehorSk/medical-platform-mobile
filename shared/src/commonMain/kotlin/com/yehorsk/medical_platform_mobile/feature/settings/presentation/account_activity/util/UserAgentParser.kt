package com.yehorsk.medical_platform_mobile.feature.settings.presentation.account_activity.util

sealed interface DeviceInfo {
    data object App : DeviceInfo
    data class Browser(val browser: String, val os: String?) : DeviceInfo
    data class Other(val label: String) : DeviceInfo
}

object UserAgentParser {

    fun parse(userAgent: String): DeviceInfo {
        val ua = userAgent.trim()
        if (APP_MARKERS.any { ua.contains(it, ignoreCase = true) }) return DeviceInfo.App

        val browser = BROWSERS.firstOrNull { (token, _) -> ua.contains(token) }?.second
        val os = OPERATING_SYSTEMS.firstOrNull { (token, _) -> ua.contains(token) }?.second

        return when {
            browser != null -> DeviceInfo.Browser(browser, os)
            os != null -> DeviceInfo.Other(os)
            else -> DeviceInfo.Other(ua.substringBefore(' ').take(MAX_RAW_LENGTH))
        }
    }

    private val APP_MARKERS = listOf("okhttp", "ktor")

    private val BROWSERS = listOf(
        "Edg/" to "Edge",
        "OPR/" to "Opera",
        "Firefox/" to "Firefox",
        "Chrome/" to "Chrome",
        "Safari/" to "Safari"
    )

    private val OPERATING_SYSTEMS = listOf(
        "Android" to "Android",
        "iPhone" to "iOS",
        "iPad" to "iPadOS",
        "Windows" to "Windows",
        "Mac OS X" to "macOS",
        "Linux" to "Linux"
    )

    private const val MAX_RAW_LENGTH = 40
}
