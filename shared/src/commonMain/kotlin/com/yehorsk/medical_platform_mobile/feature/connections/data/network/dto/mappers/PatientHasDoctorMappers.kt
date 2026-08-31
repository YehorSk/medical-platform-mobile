package com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.mappers

import com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.response.MedicalCardPatientDto
import com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.response.MedicalCardResponseDto
import com.yehorsk.medical_platform_mobile.feature.connections.data.network.dto.response.PatientHasDoctorWithoutDoctorResponse
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.MedicalCard
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.MedicalCardPatient
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.PatientHasDoctorWithoutDoctor
import com.yehorsk.medical_platform_mobile.util.getAccessStatus

fun PatientHasDoctorWithoutDoctorResponse.toDomain(): PatientHasDoctorWithoutDoctor =
    PatientHasDoctorWithoutDoctor(
        id = id,
        medicalCard = medicalCard.toDomain(),
        status = getAccessStatus(status),
        createdAt = createdAt
    )

fun MedicalCardResponseDto.toDomain(): MedicalCard =
    MedicalCard(
        id = id,
        bloodType = bloodType,
        insuranceNumber = insuranceNumber,
        patient = user?.toDomain(),
        createdAt = createdAt,
        updatedAt = updatedAt,
        dateOfBirth = dateOfBirth,
        gender = gender
    )

fun MedicalCardPatientDto.toDomain(): MedicalCardPatient =
    MedicalCardPatient(
        id = id,
        firstName = firstName,
        lastName = lastName,
        title = title
    )