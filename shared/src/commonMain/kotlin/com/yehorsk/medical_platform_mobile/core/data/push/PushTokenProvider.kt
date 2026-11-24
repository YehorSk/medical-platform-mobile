package com.yehorsk.medical_platform_mobile.core.data.push

// Platform source of the push token. Android uses FCM; platforms without push return null.
expect class PushTokenProvider {
    suspend fun getToken(): String?
    suspend fun deleteToken()
}
