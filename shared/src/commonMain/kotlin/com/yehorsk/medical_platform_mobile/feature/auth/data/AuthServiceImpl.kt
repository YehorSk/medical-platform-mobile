package com.yehorsk.medical_platform_mobile.feature.auth.data

import co.touchlab.kermit.Logger
import com.yehorsk.medical_platform_mobile.core.data.mappers.toMessageResponse
import com.yehorsk.medical_platform_mobile.core.data.network.models.MessageResponseDto
import com.yehorsk.medical_platform_mobile.core.domain.model.MessageResponse
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.core.network.get
import com.yehorsk.medical_platform_mobile.core.network.post
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.core.util.map
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.auth.data.dto.AuthDataDto
import com.yehorsk.medical_platform_mobile.feature.auth.data.dto.RefreshTokenDto
import com.yehorsk.medical_platform_mobile.feature.auth.data.dto.UserDto
import com.yehorsk.medical_platform_mobile.feature.auth.data.mappers.toAuthData
import com.yehorsk.medical_platform_mobile.feature.auth.data.mappers.toAuthDataDto
import com.yehorsk.medical_platform_mobile.feature.auth.data.mappers.toUser
import com.yehorsk.medical_platform_mobile.feature.auth.domain.AuthService
import com.yehorsk.medical_platform_mobile.feature.auth.domain.models.AuthData
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password.viewmodel.ForgotPasswordForm
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

    override suspend fun refresh(token: RefreshTokenDto): Result<AuthData, DataError.Remote> {
        return httpClient.post<RefreshTokenDto, AuthDataDto>(
            route = "/auth/refresh",
            body = token
        ).map {
            it.toAuthData()
        }
    }

    override suspend fun me(): Result<User, DataError.Remote> {
        return httpClient.get<UserDto>(
            route = "/user/me"
        ).map {
            it.toUser()
        }
    }

    override suspend fun logout(): Result<MessageResponse, DataError.Remote> {
        return httpClient.post<Unit, MessageResponseDto>(
            route = "/auth/logout",
            body = Unit
        ).map {
            it.toMessageResponse()
        }
    }

    override suspend fun forgotPassword(form: ForgotPasswordForm): Result<MessageResponse, DataError.Remote> {
        return httpClient.post<ForgotPasswordForm, MessageResponseDto>(
            route = "/auth/forgot-password",
            body = form
        ).map {
            it.toMessageResponse()
        }
    }

    override suspend fun verifyResetCode(form: ForgotPasswordForm): Result<MessageResponse, DataError.Remote> {
        return httpClient.post<ForgotPasswordForm, MessageResponseDto>(
            route = "/auth/verify-reset-code",
            body = form
        ).map {
            it.toMessageResponse()
        }
    }

    override suspend fun resetPassword(form: ForgotPasswordForm): Result<MessageResponse, DataError.Remote> {
        return httpClient.post<ForgotPasswordForm, MessageResponseDto>(
            route = "/auth/reset-password",
            body = form
        ).map {
            it.toMessageResponse()
        }
    }

}