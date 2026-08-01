package com.yehorsk.medical_platform_mobile.feature.auth.data

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.core.data.network.get
import com.yehorsk.medical_platform_mobile.core.data.network.post
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.core.util.map
import com.yehorsk.medical_platform_mobile.feature.auth.data.dto.AuthDataDto
import com.yehorsk.medical_platform_mobile.feature.auth.data.dto.RefreshTokenDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.UserResponseDto
import com.yehorsk.medical_platform_mobile.feature.auth.data.mappers.toAuthData
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

    override suspend fun login(form: LoginForm): Result<ApiResponseWithData<AuthData>, DataError.Remote> {
        return httpClient.post<LoginForm, ApiResponseWithData<AuthDataDto>>(
            route = "/auth/login",
            body = form
        ).map { response ->
            ApiResponseWithData(
                data = response.data.toAuthData(),
                message = response.message
            )
        }
    }

    override suspend fun register(form: RegisterForm): Result<ApiResponseDto, DataError.Remote> {
        return httpClient.post<RegisterForm, ApiResponseDto>(
            route = "/auth/register",
            body = form
        )
    }

    override suspend fun refresh(token: RefreshTokenDto): Result<ApiResponseWithData<AuthData>, DataError.Remote> {
        return httpClient.post<RefreshTokenDto, ApiResponseWithData<AuthDataDto>>(
            route = "/auth/refresh",
            body = token
        ).map { response ->
            ApiResponseWithData(
                data = response.data.toAuthData(),
                message = response.message
            )
        }
    }

    override suspend fun me(): Result<ApiResponseWithData<User>, DataError.Remote> {
        return httpClient.get<ApiResponseWithData<UserResponseDto>>(
            route = "/user/me"
        ).map { response ->
            ApiResponseWithData(
                data = response.data.toUser(),
                message = response.message
            )
        }
    }

    override suspend fun logout(): Result<ApiResponseDto, DataError.Remote> {
        return httpClient.post<Unit, ApiResponseDto>(
            route = "/auth/logout",
            body = Unit
        )
    }

    override suspend fun forgotPassword(form: ForgotPasswordForm): Result<ApiResponseDto, DataError.Remote> {
        return httpClient.post<ForgotPasswordForm, ApiResponseDto>(
            route = "/auth/forgot-password",
            body = form
        )
    }

    override suspend fun verifyResetCode(form: ForgotPasswordForm): Result<ApiResponseDto, DataError.Remote> {
        return httpClient.post<ForgotPasswordForm, ApiResponseDto>(
            route = "/auth/verify-reset-code",
            body = form
        )
    }

    override suspend fun resetPassword(form: ForgotPasswordForm): Result<ApiResponseDto, DataError.Remote> {
        return httpClient.post<ForgotPasswordForm, ApiResponseDto>(
            route = "/auth/reset-password",
            body = form
        )
    }

    override suspend fun verifyUsersEmail(token: String): Result<ApiResponseDto, DataError.Remote> {
        return httpClient.post(
            route = "/auth/verify",
            body = mapOf("token" to token)
        )
    }

    override suspend fun resendEmailVerification(email: String): Result<ApiResponseDto, DataError.Remote> {
        return httpClient.post(
            route = "/auth/resend",
            body = mapOf("email" to email)
        )
    }

}