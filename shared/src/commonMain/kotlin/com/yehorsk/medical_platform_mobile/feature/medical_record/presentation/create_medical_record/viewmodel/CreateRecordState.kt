package com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record.viewmodel

import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyHitRegion
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyRegion

data class CreateRecordScreenState(
    val isLoading: Boolean = false,
    val currentTab: CreateRecordTab = CreateRecordTab.DETAILS,
    val selectedRegion: BodyRegion = BodyRegion.FRONT,
    val form: CreateRecordForm = CreateRecordForm()
)

data class CreateRecordForm(
    val selectedBodyParts: List<BodyHitRegion> = emptyList()
)

enum class CreateRecordTab {
    DETAILS,  ANATOMY
}
