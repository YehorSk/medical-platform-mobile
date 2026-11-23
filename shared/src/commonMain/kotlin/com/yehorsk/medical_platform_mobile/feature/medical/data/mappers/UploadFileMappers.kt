package com.yehorsk.medical_platform_mobile.feature.medical.data.mappers

import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.UploadFile
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.extension
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.size
import io.github.vinceglb.filekit.source

// Types the backend accepts. It checks the real file content, so this is only a first filter.
object MedicalAttachmentTypes {
    private val byExtension = mapOf(
        "pdf" to "application/pdf",
        "jpg" to "image/jpeg",
        "jpeg" to "image/jpeg",
        "png" to "image/png",
        "webp" to "image/webp",
        "dcm" to "application/dicom",
        "txt" to "text/plain"
    )

    val extensions: List<String> = byExtension.keys.toList()

    fun contentTypeOf(extension: String): String? = byExtension[extension.lowercase()]
}

// Null when the file type isn't allowed
fun PlatformFile.toUploadFile(): UploadFile? {
    val contentType = MedicalAttachmentTypes.contentTypeOf(extension) ?: return null
    val file = this
    return UploadFile(
        name = name,
        contentType = contentType,
        sizeBytes = size(),
        openSource = { file.source() }
    )
}
