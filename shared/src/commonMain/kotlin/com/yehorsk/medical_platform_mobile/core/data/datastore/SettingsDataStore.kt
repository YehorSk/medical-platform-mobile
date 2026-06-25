package com.yehorsk.medical_platform_mobile.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yehorsk.medical_platform_mobile.core.domain.repository.SettingsStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDataStore(
    private val dataStore: DataStore<Preferences>
): SettingsStorage {

    private val languageKey = stringPreferencesKey("KEY_LANGUAGE")
    private val themeKey = booleanPreferencesKey("KEY_THEME")

    override fun observeLanguage(): Flow<String> {
        return dataStore.data.map { it[languageKey] ?: "EN" }
    }

    override suspend fun setLanguage(language: String) {
        dataStore.edit { it[languageKey] = language }
    }

    override fun isLightTheme(): Flow<Boolean> {
        return dataStore.data.map { it[themeKey] ?: true }
    }

    override suspend fun setTheme(theme: Boolean) {
        dataStore.edit { it[themeKey] = theme }
    }
}