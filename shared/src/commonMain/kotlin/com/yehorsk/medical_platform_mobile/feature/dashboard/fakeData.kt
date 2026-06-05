package com.yehorsk.medical_platform_mobile.feature.dashboard

import com.yehorsk.medical_platform_mobile.core.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.core.domain.model.AppointmentStatus
import com.yehorsk.medical_platform_mobile.core.domain.model.Message
import com.yehorsk.medical_platform_mobile.core.domain.model.Specialization
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole

val messages = listOf(
        Message(
            id = 1,
            conversationId = 1,
            sender = User(
                id = 1,
                email = "sarah@example.com",
                firstName = "Sarah",
                lastName = "Johnson",
                role = UserRole.DOCTOR,
                title = "Dr."
            ),
            content = "Your test results are ready",
            isRead = false,
            createdAt = "2024-11-15T10:30:00Z"
        ),
        Message(
            id = 2,
            conversationId = 2,
            sender = User(
                id = 2,
                email = "mike@example.com",
                firstName = "Mike",
                lastName = "Smith",
                role = UserRole.DOCTOR,
                title = "Dr."
            ),
            content = "Please come for a follow-up",
            isRead = true,
            createdAt = "2024-11-14T08:00:00Z"
        ),
        Message(
            id = 3,
            conversationId = 3,
            sender = User(
                id = 3,
                email = "anna@example.com",
                firstName = "Anna",
                lastName = "Brown",
                role = UserRole.DOCTOR,
                title = "Prof."
            ),
            content = "Your prescription is ready",
            isRead = true,
            createdAt = "2024-11-13T15:45:00Z"
        )
    )

val messagesEmpty = emptyList<Message>()

val appointments = listOf(
    Appointment(
        id = 1,
        datetime = "2026-06-03T10:30:00Z",
        status = AppointmentStatus.CONFIRMED,
        note = "Regular checkup",
        createdAt = "2026-06-03T10:30:00Z",
        updatedAt = "2026-06-03T10:30:00Z",
        specialization = Specialization(
            id = 1,
            name = "Cardiology"
        ),
        doctor = User(
            id = 1,
            email = "doctor@example.com",
            firstName = "John",
            lastName = "Doe",
            role = UserRole.DOCTOR,
            title = "MUDr."
        )
    ),
    Appointment(
        id = 1,
        datetime = "2026-06-03T10:30:00Z",
        status = AppointmentStatus.REJECTED,
        note = "Regular checkup",
        createdAt = "2026-06-03T10:30:00Z",
        updatedAt = "2026-06-03T10:30:00Z",
        specialization = Specialization(
            id = 1,
            name = "Dentist"
        ),
        doctor = User(
            id = 1,
            email = "doctor@example.com",
            firstName = "Sam",
            lastName = "Smith",
            role = UserRole.DOCTOR,
            title = "MUDr."
        )
    )
)