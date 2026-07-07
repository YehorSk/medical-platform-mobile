package com.yehorsk.medical_platform_mobile.feature.settings.domain

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.viewmodel.ProfileForm

interface SettingsService {

    suspend fun updateUserData(form: ProfileForm): Result<ApiResponseWithData<User>, DataError.Remote>

}