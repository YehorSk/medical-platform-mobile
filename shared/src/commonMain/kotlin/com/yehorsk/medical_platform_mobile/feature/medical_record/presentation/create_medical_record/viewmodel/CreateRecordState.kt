package com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record.viewmodel

import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyHitRegion
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyRegion
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.MedicalRecordType

data class CreateRecordScreenState(
    val isLoading: Boolean = false,
    val isConnected: Boolean = false,
    val currentTab: CreateRecordTab = CreateRecordTab.DETAILS,
    val selectedRegion: BodyRegion = BodyRegion.FRONT,
    val appointment: Appointment? = null,
    val form: CreateRecordForm = CreateRecordForm()
)

data class CreateRecordForm(
    val selectedBodyParts: List<BodyHitRegion> = emptyList(),
    val medicalRecordType: MedicalRecordType = MedicalRecordType.VISIT
)

enum class CreateRecordTab {
    DETAILS,  ANATOMY
}
