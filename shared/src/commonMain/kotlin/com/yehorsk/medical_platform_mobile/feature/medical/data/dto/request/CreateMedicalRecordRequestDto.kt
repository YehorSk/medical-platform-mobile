package com.yehorsk.medical_platform_mobile.feature.medical.data.dto.request

import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.BodyPart
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.BodyRegion
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.MedicalRecordType
import kotlinx.serialization.Serializable

@Serializable
data class CreateMedicalRecordRequestDto(
    val appointmentId: String,
    val title: String,
    val diagnosis: String,
    val recommendations: String = "",
    val type: MedicalRecordType,
    val bodyParts: Set<BodyPartSelectionDto> = mutableSetOf()
)

@Serializable
data class BodyPartSelectionDto(
    val bodyPart: BodyPart,
    val bodyRegion: BodyRegion
)