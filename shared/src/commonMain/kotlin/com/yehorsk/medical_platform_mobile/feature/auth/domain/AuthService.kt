package com.yehorsk.medical_platform_mobile.feature.auth.domain

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.auth.data.dto.RefreshTokenDto
import com.yehorsk.medical_platform_mobile.feature.auth.domain.models.AuthData
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password.viewmodel.ForgotPasswordForm
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel.LoginForm
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel.RegisterForm

interface AuthService {

    suspend fun login(form: LoginForm): Result<ApiResponseWithData<AuthData>, DataError.Remote>

    suspend fun register(form: RegisterForm): Result<ApiResponseDto, DataError.Remote>

    suspend fun refresh(token: RefreshTokenDto): Result<ApiResponseWithData<AuthData>, DataError.Remote>

    suspend fun me(): Result<ApiResponseWithData<User>, DataError.Remote>

    suspend fun logout(): Result<ApiResponseDto, DataError.Remote>

    suspend fun forgotPassword(form: ForgotPasswordForm): Result<ApiResponseDto, DataError.Remote>

    suspend fun verifyResetCode(form: ForgotPasswordForm): Result<ApiResponseDto, DataError.Remote>

    suspend fun resetPassword(form: ForgotPasswordForm): Result<ApiResponseDto, DataError.Remote>

    suspend fun verifyUsersEmail(token: String): Result<ApiResponseDto, DataError.Remote>

    suspend fun resendEmailVerification(email: String): Result<ApiResponseDto, DataError.Remote>

}