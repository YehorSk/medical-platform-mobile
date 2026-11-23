package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.viewmodel

import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.BodyHitRegion
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.BodyRegion
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.MedicalRecordType
import io.github.vinceglb.filekit.PlatformFile

data class CreateRecordScreenState(
    val isLoading: Boolean = false,
    val isConnected: Boolean = false,
    val currentTab: CreateRecordTab = CreateRecordTab.DETAILS,
    val selectedRegion: BodyRegion = BodyRegion.FRONT,
    val appointment: Appointment? = null,
    val form: CreateRecordForm = CreateRecordForm(),
    val files: List<PlatformFile> = emptyList(),
    val maxFiles: Int = MAX_FILES,
    val isSubmitting: Boolean = false,
){
    val canAddMore: Boolean get() = files.size < maxFiles && !isSubmitting
    // Files are optional; the record only needs the appointment to be loaded
    val canSubmit: Boolean get() = !isSubmitting && !isLoading && form.appointmentId.isNotBlank()

    companion object {
        const val MAX_FILES = 10
        const val MAX_FILE_BYTES = 20L * 1024 * 1024
        const val MAX_TOTAL_BYTES = 100L * 1024 * 1024
    }
}

data class CreateRecordForm(
    val patientId: String = "",
    val appointmentId: String = "",
    val selectedBodyParts: List<BodyHitRegion> = emptyList(),
    val medicalRecordType: MedicalRecordType = MedicalRecordType.VISIT,
    val title: String = "",
    val diagnosis: String = "",
    val recommendations: String = ""
)

enum class CreateRecordTab {
    DETAILS, ANATOMY
}
