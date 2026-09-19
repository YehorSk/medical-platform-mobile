package com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record.viewmodel

import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyHitRegion
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyRegion
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.MedicalRecordType

sealed interface CreateRecordAction {

    data class OnTabSelected(val tab: CreateRecordTab): CreateRecordAction

    data class OnBodyPartSelected(val part: BodyHitRegion): CreateRecordAction

    data class OnBodyRegionSelected(val region: BodyRegion): CreateRecordAction

    data class OnMedRecordTypeSelected(val type: MedicalRecordType): CreateRecordAction

    data object OnGoBackClicked: CreateRecordAction
}