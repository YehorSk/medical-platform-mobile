package com.yehorsk.medical_platform_mobile.feature.medical.data.mappers

import com.yehorsk.medical_platform_mobile.feature.medical.data.dto.request.BodyPartSelectionDto
import com.yehorsk.medical_platform_mobile.feature.medical.data.dto.request.CreateMedicalRecordRequestDto
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.BodyHitRegion
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.viewmodel.CreateRecordForm

fun CreateRecordForm.toCreateMedicalRecordRequestDto() = CreateMedicalRecordRequestDto(
    appointmentId = appointmentId,
    patientId = patientId,
    title = title,
    diagnosis = diagnosis,
    recommendations = recommendations,
    type = medicalRecordType,
    bodyParts = selectedBodyParts.map { it.toBodyPartSelectionDto() }.toSet()
)

fun BodyHitRegion.toBodyPartSelectionDto() = BodyPartSelectionDto(
    bodyPart = part,
    bodyRegion = region
)