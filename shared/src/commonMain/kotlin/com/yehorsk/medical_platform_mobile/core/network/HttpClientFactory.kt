package com.yehorsk.medical_platform_mobile.core.network

import com.yehorsk.medical_platform_mobile.core.data.network.models.RefreshRequestDto
import com.yehorsk.medical_platform_mobile.core.domain.repository.SessionStorage
import com.yehorsk.medical_platform_mobile.core.network.post
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.auth.data.dto.AuthDataDto
import com.yehorsk.medical_platform_mobile.feature.auth.data.mappers.toAuthData
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json

class HttpClientFactory(
    private val sessionStorage: SessionStorage
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
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
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
                    refreshTokens {
                        if(response.request.url.encodedPath.contains("auth/")) {
                            return@refreshTokens null
                        }
                        val authInfo = sessionStorage.observeAuthData().firstOrNull()
                        if(authInfo?.refreshToken.isNullOrBlank()) {
                            sessionStorage.clearAuthData()
                            return@refreshTokens null
                        }
                        var bearerTokens: BearerTokens? = null
                        client.post<RefreshRequestDto, AuthDataDto>(
                            route = "/auth/refresh",
                            body = RefreshRequestDto(
                                refreshToken = authInfo.refreshToken
                            ),
                            builder = {
                                markAsRefreshTokenRequest()
                            }
                        ).onSuccess { dto, _ ->
                            sessionStorage.setAuthData(dto)
                            bearerTokens = BearerTokens(
                                accessToken = dto.accessToken,
                                refreshToken = dto.refreshToken
                            )
                        }.onFailure { _ ->
                            sessionStorage.clearAuthData()
                        }
                        bearerTokens
                    }
                }
            }
        }
    }

}