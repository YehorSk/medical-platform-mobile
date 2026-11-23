package com.yehorsk.medical_platform_mobile.feature.medical.data.service

import com.yehorsk.medical_platform_mobile.core.data.network.postMultipart
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.core.util.map
import com.yehorsk.medical_platform_mobile.feature.medical.data.dto.response.CreateMedicalRecordResponseDto
import com.yehorsk.medical_platform_mobile.feature.medical.data.mappers.toCreateMedicalRecordRequestDto
import com.yehorsk.medical_platform_mobile.feature.medical.data.mappers.toCreatedMedicalRecord
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.CreatedMedicalRecord
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.NewMedicalRecord
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.UploadFile
import com.yehorsk.medical_platform_mobile.feature.medical.domain.service.MedicalRecordService
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.InputProvider
import io.ktor.client.request.forms.formData
import io.ktor.http.ContentDisposition
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.quote
import kotlinx.io.buffered
import kotlinx.serialization.json.Json

class MedicalRecordServiceImpl(
    private val httpClient: HttpClient
): MedicalRecordService {

    private val json = Json { encodeDefaults = true }

    override suspend fun createMedicalRecord(
        record: NewMedicalRecord,
        files: List<UploadFile>
    ): Result<CreatedMedicalRecord, DataError.Remote> {
        val data = json.encodeToString(record.toCreateMedicalRecordRequestDto())
        val parts = formData {
            append(
                key = "data",
                value = data,
                headers = Headers.build {
                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                }
            )
            files.forEach { file ->
                append(
                    key = "files",
                    value = InputProvider(file.sizeBytes) { file.openSource().buffered() },
                    headers = Headers.build {
                        append(HttpHeaders.ContentType, file.contentType)
                        append(
                            HttpHeaders.ContentDisposition,
                            "${ContentDisposition.Parameters.FileName}=${file.name.toSafeFilename().quote()}"
                        )
                    }
                )
            }
        }
        return httpClient
            .postMultipart<CreateMedicalRecordResponseDto>(
                route = "/medical-records",
                parts = parts
            )
            .map { it.toCreatedMedicalRecord() }
    }

    // Control characters (CR/LF etc.) in a filename would break the part headers
    private fun String.toSafeFilename(): String =
        map { if (it.isISOControl()) '_' else it }.joinToString("").ifBlank { "file" }
}
