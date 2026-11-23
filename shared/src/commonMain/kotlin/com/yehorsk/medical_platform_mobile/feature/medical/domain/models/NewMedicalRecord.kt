package com.yehorsk.medical_platform_mobile.feature.medical.domain.models

import kotlinx.io.RawSource

data class NewMedicalRecord(
    val appointmentId: String,
    val patientId: String,
    val title: String,
    val diagnosis: String,
    val recommendations: String,
    val type: MedicalRecordType,
    val bodyParts: Set<BodyPartSelection>
)

data class BodyPartSelection(
    val part: BodyPart,
    val region: BodyRegion
)

// A file picked for upload. `openSource` is called only while the request body is written,
// so files are streamed instead of being loaded into memory up front.
class UploadFile(
    val name: String,
    val contentType: String,
    val sizeBytes: Long,
    val openSource: () -> RawSource
)

data class CreatedMedicalRecord(
    val id: String,
    val message: String,
    val attachments: List<MedicalRecordAttachment>
)

data class MedicalRecordAttachment(
    val id: String,
    val originalFilename: String,
    val contentType: String?,
    val sizeBytes: Long?,
    val status: AttachmentStatus,
    val virusName: String?
)

enum class AttachmentStatus {
    PENDING_SCAN,
    AVAILABLE,
    INFECTED,
    SCAN_FAILED,
    UPLOAD_FAILED,
    UNKNOWN
}
