package com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.mappers

import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.response.AppointmentResponseDto
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentDoctor
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentPatient
import com.yehorsk.medical_platform_mobile.util.getAppointmentStatus
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
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
                title = it.title
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