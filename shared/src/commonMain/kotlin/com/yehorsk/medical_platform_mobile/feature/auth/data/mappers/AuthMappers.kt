package com.yehorsk.medical_platform_mobile.feature.auth.data.mappers

import com.yehorsk.medical_platform_mobile.feature.auth.data.dto.AuthDataDto
import com.yehorsk.medical_platform_mobile.feature.auth.data.dto.UserDto
import com.yehorsk.medical_platform_mobile.feature.auth.domain.models.AuthData
import com.yehorsk.medical_platform_mobile.feature.auth.domain.models.User

fun AuthDataDto.toAuthData() = AuthData(
    user = user.toUser(),
    message = message,
    accessToken = accessToken,
    refreshToken = refreshToken
)

fun UserDto.toUser() = User(
    id = id,
    email = email,
    firstName = firstName,
    lastName = lastName,
    role = role
)