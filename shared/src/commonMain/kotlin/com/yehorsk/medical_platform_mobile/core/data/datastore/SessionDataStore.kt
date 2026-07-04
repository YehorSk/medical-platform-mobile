package com.yehorsk.medical_platform_mobile.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import co.touchlab.kermit.Logger
import com.yehorsk.medical_platform_mobile.core.domain.repository.SessionStorage
import com.yehorsk.medical_platform_mobile.feature.auth.data.dto.AuthDataDto
import com.yehorsk.medical_platform_mobile.feature.auth.data.mappers.toAuthData
import com.yehorsk.medical_platform_mobile.feature.auth.data.mappers.toAuthDataDto
import com.yehorsk.medical_platform_mobile.feature.auth.domain.models.AuthData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import kotlin.io.encoding.Base64

class SessionDataStore(
    private val dataStore: DataStore<Preferences>
): SessionStorage {

    private val authInfoKey = stringPreferencesKey("KEY_AUTH_INFO")

    private val json = Json {
        ignoreUnknownKeys = true
    }

    override fun observeAuthData(): Flow<AuthData?> {
        return dataStore.data.map { preferences ->
            val base64 = preferences[authInfoKey]
            Logger.withTag("SessionDataStore observeAuthData").i { "${base64?.let { decode(it) }}" }
            base64?.let { decode(it) }?.toAuthData()
        }
    }

    override suspend fun setAuthData(authData: AuthDataDto) {
        Logger.i { "Original Refresh Token ${authData.refreshToken}" }
        val serialized = json.encodeToString(authData)
        val encryptedBytes = Crypto.encrypt(serialized.encodeToByteArray())
        val base64 = Base64.encode(encryptedBytes)
        dataStore.edit { prefs ->
            prefs[authInfoKey] = base64
        }
        Logger.withTag("setAuthData").i { base64 }
    }

    override suspend fun getAccessToken(): String? {
        return observeAuthData().firstOrNull()?.accessToken
    }

    override suspend fun getRefreshToken(): String? {
        return observeAuthData().firstOrNull()?.refreshToken
    }

    private fun decode(base64: String): AuthDataDto? {
        return try {
            Logger.i { "Base64 length = ${base64.length}" }

            val encryptedBytes = Base64.decode(base64)
            Logger.i { "Encrypted bytes = ${encryptedBytes.size}" }

            val decryptedBytes = Crypto.decrypt(encryptedBytes)
            Logger.i { "Decrypted bytes = ${decryptedBytes.size}" }

            val jsonString = decryptedBytes.decodeToString()
            Logger.i { "JSON = $jsonString" }

            json.decodeFromString<AuthDataDto>(jsonString)
        } catch (e: Exception) {
            Logger.e(e) { "Decode failed" }
            null
        }
    }

    override suspend fun clearAuthData() {
        dataStore.edit { prefs ->
            prefs.remove(authInfoKey)
        }
    }

}