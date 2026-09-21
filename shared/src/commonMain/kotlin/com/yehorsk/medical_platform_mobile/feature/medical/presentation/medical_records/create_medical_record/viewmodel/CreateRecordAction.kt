package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.viewmodel

import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.BodyHitRegion
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.BodyRegion
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.MedicalRecordType

sealed interface CreateRecordAction {

    data class OnTabSelected(val tab: CreateRecordTab): CreateRecordAction

    data class OnBodyPartSelected(val part: BodyHitRegion): CreateRecordAction

    data class OnBodyRegionSelected(val region: BodyRegion): CreateRecordAction

    data class OnMedRecordTypeSelected(val type: MedicalRecordType): CreateRecordAction

    data class OnTitleUpdated(val title: String): CreateRecordAction

    data class OnDiagnosisUpdated(val diagnosis: String): CreateRecordAction

    data class OnRecommendationsUpdated(val recommendations: String): CreateRecordAction

    data object OnGoBackClicked: CreateRecordAction
}