package com.yehorsk.medical_platform_mobile.core.domain.repository

import com.yehorsk.medical_platform_mobile.feature.auth.data.dto.AuthDataDto
import com.yehorsk.medical_platform_mobile.feature.auth.domain.models.AuthData
import kotlinx.coroutines.flow.Flow

interface SessionStorage {

    fun observeAuthData(): Flow<AuthData?>

    suspend fun setAuthData(authData: AuthDataDto)

    suspend fun clearAuthData()

    suspend fun getAccessToken(): String?

    suspend fun getRefreshToken(): String?

}