package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_card.viewmodel

import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.BloodType
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Gender
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.InsuranceCompany
import kotlinx.datetime.LocalDate

sealed interface MedicalCardAction {

    data class GenderSelected(
        val gender: Gender
    ) : MedicalCardAction

    data class BloodTypeSelected(
        val bloodType: BloodType
    ) : MedicalCardAction

    data class BirthOfDateChanged(
        val date: LocalDate
    ) : MedicalCardAction

    data class InsuranceProviderChanged(
        val value: InsuranceCompany
    ) : MedicalCardAction

    data class InsurancePolicyMemberIdChanged(
        val value: String
    ) : MedicalCardAction

    data object OnSaveClicked : MedicalCardAction

    data object OnGoBackClicked : MedicalCardAction

    data object ShowSaveConfirmationDialog: MedicalCardAction

    data object HideSaveConfirmationDialog: MedicalCardAction
}