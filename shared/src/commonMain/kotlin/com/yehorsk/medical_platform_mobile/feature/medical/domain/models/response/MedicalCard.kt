package com.yehorsk.medical_platform_mobile.feature.medical.domain.models.response

import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.BloodType
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Gender
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.InsuranceCompany
import kotlinx.datetime.LocalDate

data class MedicalCard(
    val id: String,
    val bloodType: BloodType?,
    val gender: Gender?,
    val insuranceProvider: InsuranceCompany?,
    val insuranceNumber: String?,
    val dateOfBirth: LocalDate?,
    val patient: MedicalCardPatient?,
    val createdAt: String,
    val updatedAt: String
)