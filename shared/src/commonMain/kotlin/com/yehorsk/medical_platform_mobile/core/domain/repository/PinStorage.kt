package com.yehorsk.medical_platform_mobile.core.domain.repository

interface PinStorage {

    suspend fun hasPin(accountId: String): Boolean

    suspend fun setPin(
        accountId: String,
        pin: String
    )

    suspend fun verifyPin(
        accountId: String,
        pin: String
    ): Boolean

    suspend fun clearPin()
}