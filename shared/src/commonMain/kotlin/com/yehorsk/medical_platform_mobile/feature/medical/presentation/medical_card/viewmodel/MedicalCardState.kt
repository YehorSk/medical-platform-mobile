package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_card.viewmodel

import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.BloodType
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Gender
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.InsuranceCompany

data class MedicalCardForm(
    val gender: Gender? = null,
    val bloodType: BloodType? = null,
    val insuranceProvider: InsuranceCompany ?= null,
    val insurancePolicyMemberId: String = "",
)

data class MedicalCardState(
    val form: MedicalCardForm = MedicalCardForm(),
    val isSaving: Boolean = false,
    val error: String? = null,
)