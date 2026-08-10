package com.yehorsk.medical_platform_mobile.core.data.mappers

import com.yehorsk.medical_platform_mobile.core.data.network.dto.request.GetDoctorsWithFilterDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ClinicResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.DayScheduleResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.DoctorDetailsResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.DoctorResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.PatientHasDoctorDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.SpecializationResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.WorkplaceResponseDto
import com.yehorsk.medical_platform_mobile.core.domain.model.Clinic
import com.yehorsk.medical_platform_mobile.core.domain.model.DaySchedule
import com.yehorsk.medical_platform_mobile.core.domain.model.Doctor
import com.yehorsk.medical_platform_mobile.core.domain.model.DoctorDetailsResponse
import com.yehorsk.medical_platform_mobile.core.domain.model.PatientHasDoctor
import com.yehorsk.medical_platform_mobile.core.domain.model.Specialization
import com.yehorsk.medical_platform_mobile.core.domain.model.WeekDay
import com.yehorsk.medical_platform_mobile.core.domain.model.Workplace
import com.yehorsk.medical_platform_mobile.core.util.ValidationErrorsDto
import com.yehorsk.medical_platform_mobile.feature.auth.data.mappers.toUser
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel.RegisterFormErrors
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.viewmodel.GetDoctorsWithFilter
import com.yehorsk.medical_platform_mobile.util.getAccessStatus
import com.yehorsk.medical_platform_mobile.util.getRole
import com.yehorsk.medical_platform_mobile.util.getWeekDay
import com.yehorsk.medical_platform_mobile.util.toDisplayTime
import kotlinx.datetime.DayOfWeek
import kotlin.time.Instant

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
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt),
    approvedAt = Instant.parse(approvedAt),
    user = user?.toUser(),
    approvedBy = approvedBy?.toUser(),
    workplace = workplace?.toWorkplace(),
    specialization = specialization?.toSpecialization(),
    currentPatientHasDoctor = currentPatientHasDoctor,
    daySchedules = schedules.map { it.toSchedule() }
)

fun GetDoctorsWithFilter.toGetDoctorsWithFilterDto() = GetDoctorsWithFilterDto(
    search = search,
    specializations = specializations.map { it.id },
    city = city,
    getPatientDoctors = showMyDoctors
)

fun PatientHasDoctorDto.toPatientHasDoctor() = PatientHasDoctor(
    id = id,
    status = getAccessStatus(status),
    initiatedBy = getRole(initiatedBy),
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun DoctorDetailsResponseDto.toDoctorDetailsResponse() = DoctorDetailsResponse(
    doctor = doctor.toDoctor(),
    access = access?.toPatientHasDoctor()
)

fun DayScheduleResponseDto.toSchedule() = DaySchedule(
    weekday = getWeekDay(weekday),
    startTime = startTime.toDisplayTime(),
    endTime = endTime.toDisplayTime(),
    lunchStart = lunchStart?.toDisplayTime(),
    lunchEnd = lunchEnd?.toDisplayTime(),
    isWorkingDay = isWorkingDay,
    slotDurationMinutes = slotDurationMinutes,
    breakBetweenMinutes = breakBetweenMinutes
)

fun WeekDay.toDayOfWeek(): DayOfWeek? =
    runCatching {
        DayOfWeek.valueOf(name)
    }.getOrNull()