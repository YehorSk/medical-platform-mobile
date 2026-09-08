package com.yehorsk.medical_platform_mobile.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.domain.repository.PinStorage
import kotlinx.coroutines.flow.first
import kotlin.io.encoding.Base64

class PinDataStore(
    private val dataStore: DataStore<Preferences>,
    private val logger: MainLogger
) : PinStorage {

    private val pinAccountIdKey =
        stringPreferencesKey("KEY_PIN_ACCOUNT_ID")

    private val pinSaltKey =
        stringPreferencesKey("KEY_PIN_SALT")

    private val pinHashKey =
        stringPreferencesKey("KEY_PIN_HASH")

    override suspend fun hasPin(accountId: String): Boolean {
        val preferences = dataStore.data.first()

        val savedAccountId = preferences[pinAccountIdKey]
            ?: return false

        return savedAccountId == accountId &&
                preferences[pinSaltKey] != null &&
                preferences[pinHashKey] != null
    }

    override suspend fun setPin(
        accountId: String,
        pin: String
    ) {
        validatePin(pin)

        val salt = PinHasher.generateSalt()

        val hash = PinHasher.hash(
            pin = pin,
            salt = salt
        )

        dataStore.edit { preferences ->
            preferences[pinAccountIdKey] = accountId
            preferences[pinSaltKey] = Base64.encode(salt)
            preferences[pinHashKey] = Base64.encode(hash)
        }

        logger.debug("PIN saved")
    }

    override suspend fun verifyPin(
        accountId: String,
        pin: String
    ): Boolean {
        if (pin.length !in 4..6 || !pin.all(Char::isDigit)) {
            return false
        }

        val preferences = dataStore.data.first()

        val savedAccountId = preferences[pinAccountIdKey]
            ?: return false

        if (savedAccountId != accountId) {
            return false
        }

        val saltBase64 = preferences[pinSaltKey]
            ?: return false

        val expectedHashBase64 = preferences[pinHashKey]
            ?: return false

        return try {
            val salt = Base64.decode(saltBase64)
            val expectedHash = Base64.decode(expectedHashBase64)

            val actualHash = PinHasher.hash(
                pin = pin,
                salt = salt
            )

            PinHasher.equals(
                first = actualHash,
                second = expectedHash
            )
        } catch (e: Exception) {
            logger.error("PIN verification failed")
            false
        }
    }

    override suspend fun clearPin() {
        dataStore.edit { preferences ->
            preferences.remove(pinAccountIdKey)
            preferences.remove(pinSaltKey)
            preferences.remove(pinHashKey)
        }

        logger.debug("PIN cleared")
    }

    private fun validatePin(pin: String) {
        require(pin.length in 4..6) {
            "PIN must contain 4 to 6 digits"
        }

        require(pin.all(Char::isDigit)) {
            "PIN must contain only digits"
        }
    }
}