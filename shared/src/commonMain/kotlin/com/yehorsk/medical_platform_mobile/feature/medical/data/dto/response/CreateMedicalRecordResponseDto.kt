package com.yehorsk.medical_platform_mobile.feature.medical.data.dto.response

import kotlinx.serialization.Serializable

// Not wrapped in ApiResponseWithData
@Serializable
data class CreateMedicalRecordResponseDto(
    val id: String,
    val message: String = "",
    val attachments: List<MedicalRecordAttachmentDto> = emptyList()
)

@Serializable
data class MedicalRecordAttachmentDto(
    val id: String,
    val originalFilename: String,
    val contentType: String? = null,
    val sizeBytes: Long? = null,
    // Kept as a string so an unknown status from the server doesn't fail the whole response
    val status: String,
    val virusName: String? = null,
    val createdAt: String? = null,
    val scannedAt: String? = null
)
