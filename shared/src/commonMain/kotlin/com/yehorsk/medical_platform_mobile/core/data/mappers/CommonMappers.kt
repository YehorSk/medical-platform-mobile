package com.yehorsk.medical_platform_mobile.core.data.mappers

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.MessageResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.SpecializationDto
import com.yehorsk.medical_platform_mobile.core.domain.model.MessageResponse
import com.yehorsk.medical_platform_mobile.core.domain.model.Specialization
import com.yehorsk.medical_platform_mobile.core.util.ValidationErrorsDto
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel.RegisterFormErrors

fun MessageResponseDto.toMessageResponse() = MessageResponse(message = message)

fun ValidationErrorsDto.toRegisterFormErrors(): RegisterFormErrors {
    val errorMap = errors.associate { it.field to it.message }

    return RegisterFormErrors(
        name = errorMap["name"].orEmpty(),
        email = errorMap["email"].orEmpty(),
        phone = errorMap["phone"].orEmpty(),
        firstName = errorMap["firstName"].orEmpty(),
        lastName = errorMap["lastName"].orEmpty(),
        role = errorMap["role"].orEmpty(),
        licenseNumber = errorMap["licenseNumber"].orEmpty(),
        password = errorMap["password"].orEmpty(),
        passwordConfirm = errorMap["passwordConfirm"].orEmpty(),
        message = message
    )
}

fun SpecializationDto.toSpecialization() = Specialization(
    id = id,
    name = name
)