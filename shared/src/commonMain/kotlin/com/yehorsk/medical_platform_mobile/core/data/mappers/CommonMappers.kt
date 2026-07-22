package com.yehorsk.medical_platform_mobile.core.data.mappers

import com.yehorsk.medical_platform_mobile.core.data.network.dto.request.GetDoctorsWithFilterDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ClinicResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.DoctorResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.MessageResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.SpecializationResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.WorkplaceResponseDto
import com.yehorsk.medical_platform_mobile.core.domain.model.Clinic
import com.yehorsk.medical_platform_mobile.core.domain.model.Doctor
import com.yehorsk.medical_platform_mobile.core.domain.model.MessageResponse
import com.yehorsk.medical_platform_mobile.core.domain.model.Specialization
import com.yehorsk.medical_platform_mobile.core.domain.model.Workplace
import com.yehorsk.medical_platform_mobile.core.util.ValidationErrorsDto
import com.yehorsk.medical_platform_mobile.feature.auth.data.mappers.toUser
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel.RegisterFormErrors
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.viewmodel.GetDoctorsWithFilter

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

fun SpecializationResponseDto.toSpecialization() = Specialization(
    id = id,
    name = name
)

fun WorkplaceResponseDto.toWorkplace() = Workplace(
    id = id,
    roomNumber = roomNumber,
    clinic = clinic.toClinic()
)

fun ClinicResponseDto.toClinic() = Clinic(
    id = id,
    name = name,
    address = address,
    phone = phone,
    city = city
)

fun DoctorResponseDto.toDoctor() = Doctor(
    id = id,
    licenseNumber = licenseNumber,
    approved = approved,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt,
    approvedAt = approvedAt,
    user = user?.toUser(),
    approvedBy = approvedBy?.toUser(),
    workplace = workplace?.toWorkplace(),
    specialization = specialization?.toSpecialization(),
    currentPatientHasDoctor = currentPatientHasDoctor
)

fun GetDoctorsWithFilter.toGetDoctorsWithFilterDto() = GetDoctorsWithFilterDto(
    search = search,
    specializations = specializations.map { it.id },
    city = city
)