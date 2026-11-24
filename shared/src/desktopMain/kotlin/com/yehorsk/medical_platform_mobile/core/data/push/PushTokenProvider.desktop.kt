package com.yehorsk.medical_platform_mobile.core.data.push

// No push support on desktop.
actual class PushTokenProvider {
    actual suspend fun getToken(): String? = null
    actual suspend fun deleteToken() = Unit
}
