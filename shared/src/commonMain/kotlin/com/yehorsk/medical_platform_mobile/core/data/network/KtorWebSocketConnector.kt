package com.yehorsk.medical_platform_mobile.core.data.network

import com.yehorsk.medical_platform_mobile.core.data.lifecycle.AppLifecycleObserver
import com.yehorsk.medical_platform_mobile.core.data.network.dto.ws.WebSocketMessageDto
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.domain.repository.SessionStorage
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.EmptyResult
import com.yehorsk.medical_platform_mobile.core.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeoutConfig
import io.ktor.client.plugins.timeout
import io.ktor.client.plugins.websocket.webSocketSession
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
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlin.coroutines.coroutineContext
import kotlin.time.Duration.Companion.seconds
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
    private val logger: MainLogger
) {
    private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)
    val connectionState = _connectionState.asStateFlow()

    private var currentSession: WebSocketSession? = null

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
                    logger.error("Exception in WebSocket", e)

                    currentSession?.close()
                    currentSession = null

                    throw connectionErrorHandler.transformException(e)
                }
                .retryWhen { t, attempt ->
                    logger.info("Connection failed on attempt $attempt")

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

    private fun createWebSocketFlow() = callbackFlow<WebSocketMessageDto> {
        _connectionState.value = ConnectionState.CONNECTING

        // Authorization добавляет Auth-плагин HttpClient (в т.ч. refresh при 401)
        val session = httpClient.webSocketSession(
            urlString = UrlConstants.BASE_URL_WS
        ) {
            timeout {
                requestTimeoutMillis = HttpTimeoutConfig.INFINITE_TIMEOUT_MS
                socketTimeoutMillis = HttpTimeoutConfig.INFINITE_TIMEOUT_MS
            }
        }
        currentSession = session
        _connectionState.value = ConnectionState.CONNECTED

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
                close(IOException("Connection closed by server"))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                close(e)
            }
        }

        awaitClose {
            reader.cancel()
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
}