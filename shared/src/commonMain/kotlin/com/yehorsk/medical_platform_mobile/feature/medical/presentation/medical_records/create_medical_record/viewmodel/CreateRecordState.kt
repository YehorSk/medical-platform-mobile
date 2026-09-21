package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.viewmodel

import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.BodyHitRegion
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.BodyRegion
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.MedicalRecordType

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
    val medicalRecordType: MedicalRecordType = MedicalRecordType.VISIT,
    val title: String = "",
    val diagnosis: String = "",
    val recommendations: String = ""
)

enum class CreateRecordTab {
    DETAILS,  ANATOMY
}
