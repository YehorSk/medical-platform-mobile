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

    /** Failed attempts persisted across app restarts. */
    suspend fun getFailedAttempts(): Int

    /** Records one failed attempt and returns the new total. */
    suspend fun registerFailedAttempt(): Int

    suspend fun resetFailedAttempts()

    /** Removes the PIN and the failed-attempt counter. */
    suspend fun clearPin()
}
