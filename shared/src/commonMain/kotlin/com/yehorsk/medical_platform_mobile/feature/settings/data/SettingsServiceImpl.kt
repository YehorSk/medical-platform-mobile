package com.yehorsk.medical_platform_mobile.feature.settings.data

import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.core.network.get
import com.yehorsk.medical_platform_mobile.core.network.post
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.core.util.map
import com.yehorsk.medical_platform_mobile.feature.auth.data.dto.UserDto
import com.yehorsk.medical_platform_mobile.feature.auth.data.mappers.toUser
import com.yehorsk.medical_platform_mobile.feature.settings.domain.SettingsService
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.viewmodel.ProfileForm
import io.ktor.client.HttpClient

class SettingsServiceImpl(
    private val httpClient: HttpClient
): SettingsService {

    override suspend fun updateUserData(form: ProfileForm): Result<User, DataError.Remote> {
        return httpClient.post<ProfileForm, UserDto>(
            route = "/user/update",
            body = form
        ).map {
            it.toUser()
        }
    }

}