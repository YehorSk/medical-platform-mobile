package com.yehorsk.medical_platform_mobile.core.data.network

import co.touchlab.kermit.Logger
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.domain.repository.SessionStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.api.Send
import io.ktor.client.plugins.api.SendingRequest
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.http.HttpHeaders
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json

class HttpClientFactory(
    private val sessionStorage: SessionStorage,
    private val tokenRefresher: TokenRefresher,
    private val logger: MainLogger
) {

    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine){
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        encodeDefaults = true
                    }
                )
            }
            install(HttpTimeout) {
                socketTimeoutMillis = 20_000L
                requestTimeoutMillis = 20_000L
            }
            install(Logging) {
                logger = object : io.ktor.client.plugins.logging.Logger {
                    override fun log(message: String) {
                        Logger.d(message)
                    }
                }
                level = LogLevel.ALL
                // Multipart uploads carry medical files (up to 100 MB); don't log them
                filter { request -> request.body !is MultiPartFormDataContent }
            }
            install(WebSockets) {
                pingIntervalMillis = 20_000L
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
            install(createMultipartContentTypeFix())
            install(createSessionGuard())
            install(Auth){
                bearer {
                    loadTokens {
                        sessionStorage
                            .observeAuthData()
                            .firstOrNull()
                            ?.let {
                                BearerTokens(
                                    accessToken = it.accessToken,
                                    refreshToken = it.refreshToken
                                )
                            }
                    }
                    sendWithoutRequest { request ->
                        !request.url.encodedPath.contains("auth/") &&
                                !request.url.encodedPath.contains("/ws/")
                    }
                    refreshTokens {
                        if(response.request.url.encodedPath.contains("auth/")) {
                            return@refreshTokens null
                        }
                        logger.debug("HttpClientFactory refresh after ${response.status} on ${response.request.url.encodedPath}")
                        val result = tokenRefresher.refresh(client, oldTokens?.accessToken) {
                            markAsRefreshTokenRequest()
                        }
                        when (result) {
                            is RefreshResult.Refreshed -> result.tokens
                            RefreshResult.LoggedOut,
                            RefreshResult.Failed -> null
                        }
                    }
                }
            }
        }
    }

    // defaultRequest sets Content-Type: application/json on every request. For multipart bodies
    // the body supplies its own `multipart/form-data; boundary=...`, so the header must go.
    private fun createMultipartContentTypeFix() = createClientPlugin("MultipartContentTypeFix") {
        on(SendingRequest) { request, content ->
            if (content is MultiPartFormDataContent) {
                request.headers.remove(HttpHeaders.ContentType)
            }
        }
    }

    private fun createSessionGuard() = createClientPlugin("SessionGuard") {
        on(Send) { request ->
            val call = proceed(request)
            val path = call.request.url.encodedPath
            val isExcluded = path.contains("auth/") ||
                    path.contains("/ws/") ||
                    path.contains("change-password")
            if (call.response.status == HttpStatusCode.Unauthorized && !isExcluded) {
                tokenRefresher.onUnauthorizedAfterRefresh(path)
            }
            call
        }
    }

}