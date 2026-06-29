package com.yehorsk.medical_platform_mobile.feature.auth.domain

import com.yehorsk.medical_platform_mobile.core.domain.model.MessageResponse
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.auth.domain.models.AuthData
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password.viewmodel.ForgotPasswordForm
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel.LoginForm
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel.RegisterForm

interface AuthService {

    suspend fun login(form: LoginForm): Result<AuthData, DataError.Remote>

    suspend fun register(form: RegisterForm): Result<MessageResponse, DataError.Remote>

    suspend fun refresh(token: String): Result<AuthData, DataError.Remote>

    suspend fun authenticate(token: String): Result<AuthData, DataError.Remote>

    suspend fun logout(): Result<MessageResponse, DataError.Remote>

    suspend fun forgotPassword(form: ForgotPasswordForm): Result<MessageResponse, DataError.Remote>

    suspend fun verifyResetCode(form: ForgotPasswordForm): Result<MessageResponse, DataError.Remote>

    suspend fun resetPassword(form: ForgotPasswordForm): Result<MessageResponse, DataError.Remote>
}