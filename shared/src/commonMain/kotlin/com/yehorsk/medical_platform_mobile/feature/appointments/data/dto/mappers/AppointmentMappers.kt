package com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.mappers

import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.request.GetAppointmentsWithFilterDto
import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.response.AppointmentResponseDto
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentDoctor
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentPatient
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentsFilter
import com.yehorsk.medical_platform_mobile.util.getAppointmentStatus
import com.yehorsk.medical_platform_mobile.util.getBloodType
import com.yehorsk.medical_platform_mobile.util.getGender
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Instant

fun AppointmentResponseDto.toAppointment(): Appointment {
    return Appointment(
        id = id,

        doctor = doctor?.let {
            AppointmentDoctor(
                id = it.id,
                firstName = it.firstName,
                lastName = it.lastName,
                title = it.title,
                specialization = it.specialization
            )
        },

        patient = patient?.let {
            AppointmentPatient(
                id = it.id,
                firstName = it.firstName,
                lastName = it.lastName,
                title = it.title,
                dateOfBirth = it.dateOfBirth,
                gender = getGender(it.gender),
                bloodType = getBloodType(it.bloodType)
            )
        },

        status = getAppointmentStatus(status),
        note = note,

        date = LocalDate.parse(date),
        time = LocalTime.parse(time),

        createdAt = Instant.parse(createdAt),
        updatedAt = Instant.parse(updatedAt)
    )
}

// Dates are whole days in the device's time zone: from = start of day, to = end of day
fun AppointmentsFilter.toGetAppointmentsWithFilterDto(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): GetAppointmentsWithFilterDto {
    return GetAppointmentsWithFilterDto(
        statuses = statuses.takeIf { it.isNotEmpty() }?.map { it.name },
        from = from?.atStartOfDayIn(timeZone)?.toString(),
        to = to?.plus(1, DateTimeUnit.DAY)?.atStartOfDayIn(timeZone)?.minus(1.milliseconds)?.toString(),
        upcoming = upcoming,
        sortDirection = sortDirection.name
    )
}