package com.yehorsk.medical_platform_mobile.feature.medical.data.mappers

import com.yehorsk.medical_platform_mobile.feature.medical.data.dto.request.BodyPartSelectionDto
import com.yehorsk.medical_platform_mobile.feature.medical.data.dto.request.CreateMedicalRecordRequestDto
import com.yehorsk.medical_platform_mobile.feature.medical.data.dto.response.CreateMedicalRecordResponseDto
import com.yehorsk.medical_platform_mobile.feature.medical.data.dto.response.MedicalRecordAttachmentDto
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.AttachmentStatus
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.BodyPartSelection
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.CreatedMedicalRecord
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.MedicalRecordAttachment
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.NewMedicalRecord

fun NewMedicalRecord.toCreateMedicalRecordRequestDto() = CreateMedicalRecordRequestDto(
    appointmentId = appointmentId,
    patientId = patientId,
    title = title.trim(),
    diagnosis = diagnosis.trim(),
    recommendations = recommendations.trim(),
    type = type,
    bodyParts = bodyParts.map { it.toBodyPartSelectionDto() }.toSet()
)

fun BodyPartSelection.toBodyPartSelectionDto() = BodyPartSelectionDto(
    bodyPart = part,
    bodyRegion = region
)

fun CreateMedicalRecordResponseDto.toCreatedMedicalRecord() = CreatedMedicalRecord(
    id = id,
    message = message,
    attachments = attachments.map { it.toMedicalRecordAttachment() }
)

fun MedicalRecordAttachmentDto.toMedicalRecordAttachment() = MedicalRecordAttachment(
    id = id,
    originalFilename = originalFilename,
    contentType = contentType,
    sizeBytes = sizeBytes,
    status = AttachmentStatus.entries.firstOrNull { it.name == status } ?: AttachmentStatus.UNKNOWN,
    virusName = virusName
)
