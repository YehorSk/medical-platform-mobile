package com.yehorsk.medical_platform_mobile.core.data.network

import com.yehorsk.medical_platform_mobile.core.data.lifecycle.AppLifecycleObserver
import com.yehorsk.medical_platform_mobile.core.data.network.dto.ws.WebSocketMessageDto
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.domain.repository.SessionStorage
import com.yehorsk.medical_platform_mobile.core.util.AuthEventManager
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.EmptyResult
import com.yehorsk.medical_platform_mobile.core.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeoutConfig
import io.ktor.client.plugins.timeout
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlin.coroutines.coroutineContext
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeSource
import io.ktor.websocket.send

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class KtorWebSocketConnector(
    private val httpClient: HttpClient,
    private val applicationScope: CoroutineScope,
    private val sessionStorage: SessionStorage,
    private val json: Json,
    private val connectionErrorHandler: ConnectionErrorHandler,
    private val connectionRetryHandler: ConnectionRetryHandler,
    private val appLifecycleObserver: AppLifecycleObserver,
    private val connectivityObserver: ConnectivityObserver,
    private val tokenRefresher: TokenRefresher,
    private val authEventManager: AuthEventManager,
    private val logger: MainLogger
) {
    private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)
    val connectionState = _connectionState.asStateFlow()

    private var currentSession: WebSocketSession? = null

    private var authFailuresInRow = 0
    private var quickReconnects = 0

    private val isConnected = connectivityObserver
        .isConnected
        .debounce(1.seconds)
        .stateIn(
            applicationScope,
            SharingStarted.WhileSubscribed(5000L),
            false
        )

    private val isInForeground = appLifecycleObserver
        .isInForeground
        .onEach { isInForeground ->
            if (isInForeground) {
                connectionRetryHandler.resetDelay()
            }
        }
        .stateIn(
            applicationScope,
            SharingStarted.WhileSubscribed(5000),
            false
        )

    // Реагируем только на факт "авторизован / нет", а не на каждое обновление токена,
    // иначе refreshTokens -> setAuthData будет перезапускать сокет.
    private val isAuthorized = sessionStorage
        .observeAuthData()
        .map { it != null }
        .distinctUntilChanged()

    val messages: Flow<WebSocketMessageDto> = combine(
        isAuthorized,
        isConnected,
        isInForeground
    ) { authorized, isConnected, isInForeground ->
        when {
            !authorized -> {
                logger.info("No authentication details. Clearing session and disconnecting...")
                _connectionState.value = ConnectionState.DISCONNECTED
                currentSession?.close()
                currentSession = null
                connectionRetryHandler.resetDelay()
                false
            }

            !isInForeground -> {
                logger.info("App in background, disconnecting socket proactively.")
                _connectionState.value = ConnectionState.DISCONNECTED
                currentSession?.close()
                currentSession = null
                false
            }

            !isConnected -> {
                logger.info("Device is disconnected, closing WebSocket connection.")
                _connectionState.value = ConnectionState.ERROR_NETWORK
                currentSession?.close()
                currentSession = null
                false
            }

            else -> {
                logger.info("App in foreground & connected. Establishing connection...")

                if (_connectionState.value !in listOf(
                        ConnectionState.CONNECTING,
                        ConnectionState.CONNECTED
                    )
                ) {
                    _connectionState.value = ConnectionState.CONNECTING
                }

                true
            }
        }
    }.flatMapLatest { shouldConnect ->
        if (!shouldConnect) {
            emptyFlow()
        } else {
            createWebSocketFlow()
                // Преобразуем исключения для платформенной совместимости
                .catch { e ->
                    if (e is SocketAuthException) {
                        logger.info("WebSocket closed: ${e.message}")
                    } else {
                        logger.error("Exception in WebSocket", e)
                    }

                    currentSession?.close()
                    currentSession = null

                    throw connectionErrorHandler.transformException(e)
                }
                .retryWhen { t, attempt ->
                    logger.info("Connection failed on attempt $attempt")

                    if (t is SocketAuthException) {
                        return@retryWhen handleAuthClose(t)
                    }

                    val shouldRetry = connectionRetryHandler.shouldRetry(t, attempt)

                    if (shouldRetry) {
                        _connectionState.value = ConnectionState.CONNECTING
                        connectionRetryHandler.applyRetryDelay(attempt)
                    }

                    shouldRetry
                }
                // Ошибки, после которых повторять не нужно
                .catch { e ->
                    logger.error("Unhandled WebSocket error", e)

                    _connectionState.value = connectionErrorHandler.getConnectionStateForError(e)
                }
        }
    }

    /**
     * The server closes the socket with 1008 once the handshake access token expires (or if it was
     * invalid). Refresh and reconnect; only a second "Authentication failed" in a row ends the session.
     */
    private suspend fun handleAuthClose(cause: SocketAuthException): Boolean {
        _connectionState.value = ConnectionState.CONNECTING

        when (cause) {
            is SocketAuthException.Renewed -> {
                authFailuresInRow = 0
                quickReconnects = 0
                return true
            }
            is SocketAuthException.TokenExpired -> authFailuresInRow = 0
            is SocketAuthException.AuthFailed -> {
                if (cause.sessionLifetime > STABLE_SESSION_DURATION) authFailuresInRow = 0
                authFailuresInRow++
                if (authFailuresInRow > 1) {
                    logger.info("WebSocket auth failed again after refresh, logging out")
                    authFailuresInRow = 0
                    sessionStorage.clearAuthData()
                    authEventManager.triggerLogout()
                    return false
                }
            }
        }

        // Expiry is routine, but a socket that keeps dying right after connecting (e.g. clock skew)
        // must not spin: back off on quick repeats.
        quickReconnects = if (cause.sessionLifetime < STABLE_SESSION_DURATION) quickReconnects + 1 else 0
        if (quickReconnects > 1) {
            connectionRetryHandler.applyRetryDelay(quickReconnects.toLong())
        }

        return when (tokenRefresher.refresh(httpClient, cause.accessToken)) {
            is RefreshResult.Refreshed -> {
                logger.info("Tokens refreshed after WebSocket close (${cause.message}), reconnecting")
                true
            }
            RefreshResult.LoggedOut -> {
                logger.info("Refresh rejected, not reconnecting WebSocket")
                false
            }
            RefreshResult.Failed -> {
                // Network trouble: don't count it as an auth failure, try again after a pause
                authFailuresInRow = 0
                connectionRetryHandler.applyRetryDelay(maxOf(quickReconnects, 1).toLong())
                true
            }
        }
    }

    private fun createWebSocketFlow() = callbackFlow<WebSocketMessageDto> {
        _connectionState.value = ConnectionState.CONNECTING

        // The handshake accepts only an access token. It's read from storage, not from the Auth plugin's
        // cache, which only refreshes on a REST 401 and may hold an expired token by now.
        val accessToken = sessionStorage.getAccessToken()
        if (accessToken == null) {
            _connectionState.value = ConnectionState.DISCONNECTED
            close()
            return@callbackFlow
        }

        val session = httpClient.webSocketSession(
            urlString = UrlConstants.BASE_URL_WS
        ) {
            header(HttpHeaders.Authorization, "Bearer $accessToken")
            timeout {
                requestTimeoutMillis = HttpTimeoutConfig.INFINITE_TIMEOUT_MS
                socketTimeoutMillis = HttpTimeoutConfig.INFINITE_TIMEOUT_MS
            }
        }
        val connectedAt = TimeSource.Monotonic.markNow()
        currentSession = session
        _connectionState.value = ConnectionState.CONNECTED

        // Renew a bit before `exp` so the server never has to drop the socket under the user.
        // Skipped when the token is already that close to expiry, the server close covers it then.
        val renewer = launch {
            val expiresAt = JwtUtils.expiresAt(accessToken) ?: return@launch
            val renewIn = expiresAt - Clock.System.now() - PROACTIVE_RENEW_MARGIN
            if (renewIn <= Duration.ZERO) return@launch
            delay(renewIn)
            if (tokenRefresher.refresh(httpClient, accessToken) is RefreshResult.Refreshed) {
                logger.info("Access token renewed ahead of expiry, reconnecting WebSocket")
                close(SocketAuthException.Renewed(accessToken, connectedAt.elapsedNow()))
            }
        }

        val reader = launch {
            try {
                for (frame in session.incoming) {
                    if (frame is Frame.Text) {
                        val text = frame.readText()
                        logger.info("Received raw text frame: $text")

                        try {
                            val dto = json.decodeFromString<WebSocketMessageDto>(text)
                            trySend(dto)
                        } catch (e: SerializationException) {
                            // битое сообщение не должно ронять соединение
                            logger.error("Malformed message skipped", e)
                        }
                    }
                    // Ping/Pong обрабатывает Ktor сам (pingIntervalMillis)
                }
                // Сервер закрыл соединение -> ошибка, чтобы сработал retryWhen
                val reason = session.closeReason.await()
                logger.info("WebSocket closed by server: $reason")
                close(
                    SocketAuthException.from(reason, accessToken, connectedAt.elapsedNow())
                        ?: IOException("Connection closed by server: $reason")
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                close(e)
            }
        }

        awaitClose {
            reader.cancel()
            renewer.cancel()
            launch(NonCancellable) {
                logger.info("Disconnecting from WebSocket session...")
                session.close()
                if (currentSession === session) {
                    currentSession = null
                }
            }
        }
    }

    suspend fun sendMessage(message: String): EmptyResult<DataError.Connection> {
        val session = currentSession

        if (session == null || connectionState.value != ConnectionState.CONNECTED) {
            return Result.Failure(DataError.Connection.NOT_CONNECTED)
        }

        return try {
            session.send(message)
            Result.Success(Unit)
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            logger.error("Unable to send WebSocket message", e)
            Result.Failure(DataError.Connection.MESSAGE_SEND_FAILED)
        }
    }

    private companion object {
        val PROACTIVE_RENEW_MARGIN = 60.seconds
        val STABLE_SESSION_DURATION = 60.seconds
    }
}

/** Socket ended for an auth reason; [accessToken] is the token the socket was opened with. */
sealed class SocketAuthException(
    val accessToken: String,
    val sessionLifetime: Duration,
    message: String
) : IOException(message) {
    class TokenExpired(accessToken: String, lifetime: Duration) :
        SocketAuthException(accessToken, lifetime, "Token expired")

    class AuthFailed(accessToken: String, lifetime: Duration) :
        SocketAuthException(accessToken, lifetime, "Authentication failed")

    // Closed by us after renewing the token ahead of expiry
    class Renewed(accessToken: String, lifetime: Duration) :
        SocketAuthException(accessToken, lifetime, "Token renewed")

    companion object {
        fun from(reason: CloseReason?, accessToken: String, lifetime: Duration): SocketAuthException? {
            if (reason?.code != CloseReason.Codes.VIOLATED_POLICY.code) return null
            return when (reason.message) {
                "Token expired" -> TokenExpired(accessToken, lifetime)
                "Authentication failed" -> AuthFailed(accessToken, lifetime)
                else -> null
            }
        }
    }
}