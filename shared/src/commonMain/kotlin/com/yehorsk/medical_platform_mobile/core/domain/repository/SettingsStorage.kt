package com.yehorsk.medical_platform_mobile.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsStorage {

    fun observeLanguage(): Flow<String>

    suspend fun setLanguage(language: String)

    fun isLightTheme(): Flow<Boolean>

    suspend fun setTheme(theme: Boolean)

}