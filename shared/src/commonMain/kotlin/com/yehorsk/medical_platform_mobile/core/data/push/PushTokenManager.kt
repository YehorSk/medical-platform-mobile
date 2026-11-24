package com.yehorsk.medical_platform_mobile.core.data.push

import co.touchlab.kermit.Logger
import com.yehorsk.medical_platform_mobile.core.domain.repository.SessionStorage
import com.yehorsk.medical_platform_mobile.core.domain.service.DeviceTokenService
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Keeps the backend's device-token registration in sync with the session:
 * - a session appears (login or app start with a saved session) -> register the token
 * - FCM rotates the token while logged in -> register the new one
 * - the session goes away (logout or forced logout) -> delete the token locally,
 *   which invalidates it at FCM even when the backend can no longer be reached.
 *
 * On a normal logout, call [unregisterFromBackend] before clearing the session,
 * while the access token is still valid.
 */
class PushTokenManager(
    private val sessionStorage: SessionStorage,
    private val deviceTokenService: DeviceTokenService,
    private val pushTokenProvider: PushTokenProvider,
    private val applicationScope: CoroutineScope
) {

    private val logger = Logger.withTag("PushTokenManager")
    private var previousUserId: String? = null

    init {
        sessionStorage
            .observeAuthData()
            // Token refreshes re-emit auth data; only react when the user changes.
            .map { it?.user?.id }
            .distinctUntilChanged()
            .onEach { userId ->
                when {
                    userId != null -> registerCurrentToken()
                    // Only on a real logout, not on every start without a session.
                    previousUserId != null -> deleteLocalToken()
                }
                previousUserId = userId
            }
            .launchIn(applicationScope)
    }

    fun onNewToken(token: String) {
        applicationScope.launch {
            if (sessionStorage.observeAuthData().first() != null) {
                register(token)
            }
        }
    }

    suspend fun unregisterFromBackend() {
        val token = runCatchingToken { pushTokenProvider.getToken() } ?: return
        deviceTokenService.unregister(token)
            .onFailure { logger.w { "Failed to unregister device token: $it" } }
    }

    private suspend fun registerCurrentToken() {
        val token = runCatchingToken { pushTokenProvider.getToken() } ?: return
        register(token)
    }

    private suspend fun register(token: String) {
        deviceTokenService.register(token)
            .onFailure { logger.w { "Failed to register device token: $it" } }
    }

    private suspend fun deleteLocalToken() {
        runCatchingToken { pushTokenProvider.deleteToken() }
    }

    // FCM calls fail without Play Services or network; push is best-effort and must not crash the app.
    private suspend fun <T> runCatchingToken(block: suspend () -> T): T? {
        return try {
            block()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            logger.w(e) { "Push token operation failed" }
            null
        }
    }

}
