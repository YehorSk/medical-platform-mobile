package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_card.viewmodel

import com.kizitonwose.calendar.core.now
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.BloodType
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Gender
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.InsuranceCompany
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.response.MedicalCard
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

data class MedicalCardState(
    val form: MedicalCardForm = MedicalCardForm(),
    val isConnected: Boolean = false,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val error: String? = null,
    val showUpdateConfirmation: Boolean = false,
    val medicalCard: MedicalCard? = null
)

@Serializable
data class MedicalCardForm(
    val gender: Gender? = null,
    val bloodType: BloodType? = null,
    val insuranceCompany: InsuranceCompany ?= null,
    val insuranceNumber: String = "",
    val dateOfBirth: LocalDate? = LocalDate.now()
)