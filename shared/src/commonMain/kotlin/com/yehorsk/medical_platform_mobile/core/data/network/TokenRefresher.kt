package com.yehorsk.medical_platform_mobile.core.data.network

import com.yehorsk.medical_platform_mobile.core.data.network.dto.request.RefreshRequestDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.domain.repository.SessionStorage
import com.yehorsk.medical_platform_mobile.core.util.AuthEventManager
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.auth.data.dto.AuthDataDto
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.request.HttpRequestBuilder
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

sealed interface RefreshResult {
    data class Refreshed(val tokens: BearerTokens) : RefreshResult
    // Refresh token is gone or rejected: the session is over
    data object LoggedOut : RefreshResult
    // Network/server problem: keep the session and try again later
    data object Failed : RefreshResult
}

/**
 * Single place that calls /auth/refresh. Used by the Ktor Auth plugin (REST 401) and by the
 * WebSocket connector (expired socket token). The backend rotates refresh tokens, so two parallel
 * refreshes with the same refresh token would log the user out; the mutex prevents that.
 */
class TokenRefresher(
    private val sessionStorage: SessionStorage,
    private val authEventManager: AuthEventManager,
    private val logger: MainLogger
) {
    private val mutex = Mutex()

    /**
     * @param staleAccessToken the access token that was rejected. If storage already holds a different
     * one, another caller refreshed in the meantime and that token is returned without a new request.
     */
    suspend fun refresh(
        client: HttpClient,
        staleAccessToken: String?,
        // From the Auth plugin's refreshTokens block (markAsRefreshTokenRequest only exists there).
        // Callers outside it can skip it: the plugin never refreshes for auth/ routes anyway.
        markRequest: HttpRequestBuilder.() -> Unit = {}
    ): RefreshResult = mutex.withLock {
        val authInfo = sessionStorage.observeAuthData().firstOrNull()
        if (authInfo == null || authInfo.refreshToken.isBlank()) {
            sessionStorage.clearAuthData()
            return@withLock RefreshResult.LoggedOut
        }

        if (staleAccessToken != null && authInfo.accessToken != staleAccessToken) {
            return@withLock RefreshResult.Refreshed(
                BearerTokens(authInfo.accessToken, authInfo.refreshToken)
            )
        }

        val result = client.post<RefreshRequestDto, ApiResponseWithData<AuthDataDto>>(
            route = "/auth/refresh",
            body = RefreshRequestDto(refreshToken = authInfo.refreshToken),
            builder = { markRequest() }
        )

        when (result) {
            is Result.Success -> {
                sessionStorage.setAuthData(result.data.data)
                RefreshResult.Refreshed(
                    BearerTokens(
                        accessToken = result.data.data.accessToken,
                        refreshToken = result.data.data.refreshToken
                    )
                )
            }
            is Result.Failure -> {
                logger.debug("TokenRefresher refresh failed -> ${result.error}")
                if (result.error.isSessionRejected()) {
                    sessionStorage.clearAuthData()
                    authEventManager.triggerLogout()
                    RefreshResult.LoggedOut
                } else {
                    RefreshResult.Failed
                }
            }
        }
    }

    private fun DataError.Remote.isSessionRejected(): Boolean = when (this) {
        DataError.Remote.Status.UNAUTHORIZED,
        DataError.Remote.Status.FORBIDDEN,
        DataError.Remote.Status.BAD_REQUEST,
        DataError.Remote.Status.NOT_FOUND -> true
        else -> false
    }
}
