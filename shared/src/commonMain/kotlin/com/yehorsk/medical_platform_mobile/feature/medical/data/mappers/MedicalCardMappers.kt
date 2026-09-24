package com.yehorsk.medical_platform_mobile.feature.medical.data.mappers

import com.yehorsk.medical_platform_mobile.feature.medical.data.dto.response.MedicalCardPatientDto
import com.yehorsk.medical_platform_mobile.feature.medical.data.dto.response.MedicalCardResponseDto
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.response.MedicalCard
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.response.MedicalCardPatient
import com.yehorsk.medical_platform_mobile.util.getBloodType
import com.yehorsk.medical_platform_mobile.util.getGender
import com.yehorsk.medical_platform_mobile.util.getInsuranceProvider
import kotlinx.datetime.LocalDate

fun MedicalCardResponseDto.toDomain(): MedicalCard =
    MedicalCard(
        id = id,
        bloodType = getBloodType(bloodType),
        insuranceNumber = insuranceNumber,
        patient = patient?.toDomain(),
        createdAt = createdAt,
        updatedAt = updatedAt,
        dateOfBirth = dateOfBirth.let { LocalDate.parse(it) },
        gender = getGender(gender),
        insuranceProvider = getInsuranceProvider(insuranceProvider)
    )

fun MedicalCardPatientDto.toDomain(): MedicalCardPatient =
    MedicalCardPatient(
        id = id,
        firstName = firstName,
        lastName = lastName,
        title = title
    )