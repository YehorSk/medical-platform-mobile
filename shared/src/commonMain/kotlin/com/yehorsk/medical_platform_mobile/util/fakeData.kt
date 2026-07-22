package com.yehorsk.medical_platform_mobile.util

import com.yehorsk.medical_platform_mobile.core.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.core.domain.model.AppointmentStatus
import com.yehorsk.medical_platform_mobile.core.domain.model.Clinic
import com.yehorsk.medical_platform_mobile.core.domain.model.Conversation
import com.yehorsk.medical_platform_mobile.core.domain.model.Doctor
import com.yehorsk.medical_platform_mobile.core.domain.model.Message
import com.yehorsk.medical_platform_mobile.core.domain.model.Specialization
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.domain.model.Workplace
import kotlin.time.Instant

val conversations = listOf(
    Conversation(
        id = 1,
        createdAt = Instant.parse("2026-06-01T10:00:00Z"),
        patient = User(
            id = "1",
            email = "john.doe@example.com",
            firstName = "John",
            lastName = "Doe",
            role = "PATIENT",
            title = "Mr.",
            createdAt = Instant.parse("2026-06-03T10:30:00Z"),
        ),
        doctor = User(
            id = "2",
            email = "anna.smith@example.com",
            firstName = "Anna",
            lastName = "Smith",
            role = "PATIENT",
            title = "Dr.",
            createdAt = Instant.parse("2026-06-03T10:30:00Z"),
        ),
        lastMessage = Message(
            id = 1,
            conversationId = 1,
            sender = User(
                id = "1",
                email = "john.doe@example.com",
                firstName = "John",
                lastName = "Doe",
                role = "PATIENT",
                title = "Mr.",
                createdAt = Instant.parse("2026-06-03T10:30:00Z"),
            ),
            content = "Hello doctor, I have a question.",
            isRead = true,
            createdAt = Instant.parse("2026-06-01T10:05:00Z")
        )
    ),
    Conversation(
        id = 2,
        createdAt = Instant.parse("2026-06-02T14:30:00Z"),
        patient = User(
            id = "3",
            email = "maria.wilson@example.com",
            firstName = "Maria",
            lastName = "Wilson",
            role = "PATIENT",
            title = "Mrs.",
            createdAt = Instant.parse("2026-06-03T10:30:00Z"),
        ),
        doctor = User(
            id = "4",
            email = "peter.brown@example.com",
            firstName = "Peter",
            lastName = "Brown",
            role = "PATIENT",
            title = "Dr.",
            createdAt = Instant.parse("2026-06-03T10:30:00Z"),
        ),
        lastMessage = Message(
            id = 2,
            conversationId = 2,
            sender = User(
                id = "4",
                email = "peter.brown@example.com",
                firstName = "Peter",
                lastName = "Brown",
                role = "PATIENT",
                title = "Dr.",
                createdAt = Instant.parse("2026-06-03T10:30:00Z"),
            ),
            content = "Your test results look good.",
            isRead = false,
            createdAt = Instant.parse("2026-06-02T14:45:00Z")
        )
    ),
    Conversation(
        id = 3,
        createdAt = Instant.parse("2026-06-03T09:15:00Z"),
        patient = User(
            id = "5",
            email = "alex.miller@example.com",
            firstName = "Alex",
            lastName = "Miller",
            role = "PATIENT",
            title = "Mr.",
            createdAt = Instant.parse("2026-06-03T10:30:00Z"),
        ),
        doctor = User(
            id = "6",
            email = "sarah.johnson@example.com",
            firstName = "Sarah",
            lastName = "Johnson",
            role = "DOCTOR",
            title = "Dr.",
            createdAt = Instant.parse("2026-06-03T10:30:00Z"),
        ),
        lastMessage = Message(
            id = 3,
            conversationId = 3,
            sender = User(
                id = "5",
                email = "alex.miller@example.com",
                firstName = "Alex",
                lastName = "Miller",
                role = "PATIENT",
                title = "Mr.",
                createdAt = Instant.parse("2026-06-03T10:30:00Z"),
            ),
            content = "Thank you for your help.",
            isRead = true,
            createdAt = Instant.parse("2026-06-03T09:20:00Z")
        )
    )
)

val messagesEmpty = emptyList<Message>()

val appointments = listOf(
    Appointment(
        id = 1,
        datetime = Instant.parse("2026-06-03T10:30:00Z"),
        status = AppointmentStatus.CONFIRMED,
        note = "Regular checkup",
        createdAt = Instant.parse("2026-06-03T10:30:00Z"),
        updatedAt = Instant.parse("2026-06-03T10:30:00Z"),
        specialization = Specialization(
            id = "1",
            name = "Cardiology"
        ),
        doctor = User(
            id = "1",
            email = "doctor@example.com",
            firstName = "John",
            lastName = "Doe",
            role = "DOCTOR",
            title = "MUDr.",
            createdAt = Instant.parse("2026-06-03T10:30:00Z"),
        )
    ),
    Appointment(
        id = 2,
        datetime = Instant.parse("2026-06-03T10:30:00Z"),
        status = AppointmentStatus.REJECTED,
        note = "Regular checkup",
        createdAt = Instant.parse("2026-06-03T10:30:00Z"),
        updatedAt = Instant.parse("2026-06-03T10:30:00Z"),
        specialization = Specialization(
            id = "1",
            name = "Dentist"
        ),
        doctor = User(
            id = "1",
            email = "doctor@example.com",
            firstName = "Sam",
            lastName = "Smith",
            role = "DOCTOR",
            title = "MUDr.",
            createdAt = Instant.parse("2026-06-03T10:30:00Z"),
        )
    )
)

val fakeDoctor = Doctor(
    id = "cc75b982-0f3e-45f0-a601-23278c2e128b",
    licenseNumber = "LIC-001-1784566413864",
    user = User(
        id = "160eccf4-ed8d-4970-bea4-45678e87d7d6",
        email = "doctor1@example.com",
        firstName = "James",
        lastName = "Smith",
        role = "DOCTOR",
        title = "Dr.",
        phone = "+1-555-0123",
        address = "123 Main Street",
        emergencyContactName = "Anna Smith",
        emergencyContactPhone = "+1-555-0456"
    ),
    approvedBy = null,
    approved = true,
    description = "Experienced cardiologist providing quality patient care.",
    specialization = Specialization(
        id = "92dac9ca-ae7f-4b82-b292-af565ddc2a04",
        name = "Cardiology"
    ),
    workplace = Workplace(
        id = "2b375d92-1d47-4655-afeb-f4388d900ff1",
        roomNumber = "Room 100",
        clinic = Clinic(
            id = "6c807210-d093-4506-9c6c-d63c5b71950d",
            name = "Central Medical Clinic",
            address = "123 Main Street",
            phone = "+1-555-0123",
            city = "New York"
        )
    ),
    createdAt = "2026-07-20T16:53:33.883072Z",
    updatedAt = "2026-07-20T16:53:33.883072Z",
    approvedAt = "2026-07-20T16:53:33.868806Z",
    currentPatientHasDoctor = false
)