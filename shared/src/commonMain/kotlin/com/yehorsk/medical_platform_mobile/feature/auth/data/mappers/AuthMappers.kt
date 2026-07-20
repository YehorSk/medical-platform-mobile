package com.yehorsk.medical_platform_mobile.feature.auth.data.mappers

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.PagedResponseDto
import com.yehorsk.medical_platform_mobile.feature.auth.data.dto.AuthDataDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.UserResponseDto
import com.yehorsk.medical_platform_mobile.feature.auth.domain.models.AuthData
import com.yehorsk.medical_platform_mobile.core.domain.model.User

fun AuthDataDto.toAuthData() = AuthData(
    user = user.toUser(),
    accessToken = accessToken,
    refreshToken = refreshToken
)

fun AuthData.toAuthDataDto() = AuthDataDto(
    user = user.toUserDto(),
    accessToken = accessToken,
    refreshToken = refreshToken
)

fun UserResponseDto.toUser() = User(
    id = id,
    email = email,
    firstName = firstName,
    lastName = lastName,
    role = role,
    phone = phone,
    address = address,
    title = title,
    emergencyContactName = emergencyContactName,
    emergencyContactPhone = emergencyContactPhone
)

fun User.toUserDto() = UserResponseDto(
    id = id,
    email = email,
    firstName = firstName,
    lastName = lastName,
    role = role,
    title = title ,
    phone = phone,
    address = address,
    emergencyContactName = emergencyContactName,
    emergencyContactPhone = emergencyContactPhone
)