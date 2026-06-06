package com.yehorsk.medical_platform_mobile.util

import com.yehorsk.medical_platform_mobile.core.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.core.domain.model.AppointmentStatus
import com.yehorsk.medical_platform_mobile.core.domain.model.Conversation
import com.yehorsk.medical_platform_mobile.core.domain.model.Message
import com.yehorsk.medical_platform_mobile.core.domain.model.Specialization
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole

val conversations = listOf(
    Conversation(
        id = 1,
        createdAt = "2026-06-01T10:00:00Z",
        patient = User(
            id = 1,
            email = "john.doe@example.com",
            firstName = "John",
            lastName = "Doe",
            role = UserRole.PATIENT,
            title = "Mr."
        ),
        doctor = User(
            id = 2,
            email = "anna.smith@example.com",
            firstName = "Anna",
            lastName = "Smith",
            role = UserRole.DOCTOR,
            title = "Dr."
        ),
        lastMessage = Message(
            id = 1,
            conversationId = 1,
            sender = User(
                id = 1,
                email = "john.doe@example.com",
                firstName = "John",
                lastName = "Doe",
                role = UserRole.PATIENT,
                title = "Mr."
            ),
            content = "Hello doctor, I have a question.",
            isRead = true,
            createdAt = "2026-06-01T10:05:00Z"
        )
    ),
    Conversation(
        id = 2,
        createdAt = "2026-06-02T14:30:00Z",
        patient = User(
            id = 3,
            email = "maria.wilson@example.com",
            firstName = "Maria",
            lastName = "Wilson",
            role = UserRole.PATIENT,
            title = "Mrs."
        ),
        doctor = User(
            id = 4,
            email = "peter.brown@example.com",
            firstName = "Peter",
            lastName = "Brown",
            role = UserRole.DOCTOR,
            title = "Dr."
        ),
        lastMessage = Message(
            id = 2,
            conversationId = 2,
            sender = User(
                id = 4,
                email = "peter.brown@example.com",
                firstName = "Peter",
                lastName = "Brown",
                role = UserRole.DOCTOR,
                title = "Dr."
            ),
            content = "Your test results look good.",
            isRead = false,
            createdAt = "2026-06-02T14:45:00Z"
        )
    ),
    Conversation(
        id = 3,
        createdAt = "2026-06-03T09:15:00Z",
        patient = User(
            id = 5,
            email = "alex.miller@example.com",
            firstName = "Alex",
            lastName = "Miller",
            role = UserRole.PATIENT,
            title = "Mr."
        ),
        doctor = User(
            id = 6,
            email = "sarah.johnson@example.com",
            firstName = "Sarah",
            lastName = "Johnson",
            role = UserRole.DOCTOR,
            title = "Dr."
        ),
        lastMessage = Message(
            id = 3,
            conversationId = 3,
            sender = User(
                id = 5,
                email = "alex.miller@example.com",
                firstName = "Alex",
                lastName = "Miller",
                role = UserRole.PATIENT,
                title = "Mr."
            ),
            content = "Thank you for your help.",
            isRead = true,
            createdAt = "2026-06-03T09:20:00Z"
        )
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