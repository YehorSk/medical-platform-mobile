package com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.mappers

import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.response.AppointmentResponseDto
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.feature.auth.data.mappers.toUser
import com.yehorsk.medical_platform_mobile.util.getAppointmentStatus
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlin.time.Instant

fun AppointmentResponseDto.toAppointment() = Appointment(
    id = id,
    doctor = doctor?.toUser(),
    patient = patient?.toUser(),
    status = getAppointmentStatus(status),
    note = note,
    date = LocalDate.parse(date),
    time = LocalTime.parse(time),
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt)
)