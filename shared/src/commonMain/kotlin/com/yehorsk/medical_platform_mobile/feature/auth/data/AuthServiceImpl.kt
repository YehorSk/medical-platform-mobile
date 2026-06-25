package com.yehorsk.medical_platform_mobile.feature.auth.data

import com.yehorsk.medical_platform_mobile.core.data.mappers.toMessageResponse
import com.yehorsk.medical_platform_mobile.core.data.network.models.MessageResponseDto
import com.yehorsk.medical_platform_mobile.core.domain.model.MessageResponse
import com.yehorsk.medical_platform_mobile.core.network.post
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.core.util.map
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.auth.data.dto.AuthDataDto
import com.yehorsk.medical_platform_mobile.feature.auth.data.mappers.toAuthData
import com.yehorsk.medical_platform_mobile.feature.auth.domain.AuthService
import com.yehorsk.medical_platform_mobile.feature.auth.domain.models.AuthData
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel.LoginForm
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel.RegisterForm
import io.ktor.client.HttpClient

class AuthServiceImpl(
    private val httpClient: HttpClient
): AuthService {

    override suspend fun login(form: LoginForm): Result<AuthData, DataError.Remote> {
        return httpClient.post<LoginForm, AuthDataDto>(
            route = "/auth/login",
            body = form
        ).map {
            it.toAuthData()
        }
    }

    override suspend fun register(form: RegisterForm): Result<MessageResponse, DataError.Remote> {
        return httpClient.post<RegisterForm, MessageResponseDto>(
            route = "/auth/register",
            body = form
        ).map {
            it.toMessageResponse()
        }
    }

}