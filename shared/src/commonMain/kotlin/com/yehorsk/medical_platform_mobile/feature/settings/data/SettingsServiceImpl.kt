package com.yehorsk.medical_platform_mobile.feature.settings.data

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.core.data.network.post
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.core.util.map
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.UserResponseDto
import com.yehorsk.medical_platform_mobile.feature.auth.data.mappers.toUser
import com.yehorsk.medical_platform_mobile.feature.settings.domain.SettingsService
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.update_password.viewmodel.UpdatePwdForm
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.viewmodel.ProfileForm
import io.ktor.client.HttpClient

class SettingsServiceImpl(
    private val httpClient: HttpClient
): SettingsService {

    override suspend fun updateUserData(form: ProfileForm): Result<ApiResponseWithData<User>, DataError.Remote> {
        return httpClient.post<ProfileForm, ApiResponseWithData<UserResponseDto>>(
            route = "/user/update",
            body = form
        ).map { response ->
            ApiResponseWithData(
                data = response.data.toUser(),
                message = response.message
            )
        }
    }

    override suspend fun updatePassword(form: UpdatePwdForm): Result<ApiResponseDto, DataError.Remote> {
        return httpClient.post<UpdatePwdForm, ApiResponseDto>(
            route = "/user/change-password",
            body = form
        ).map { response ->
            ApiResponseDto(
                message = response.message
            )
        }
    }

}