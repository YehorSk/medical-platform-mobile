package com.yehorsk.medical_platform_mobile.feature.medical.domain.service

import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.CreatedMedicalRecord
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.NewMedicalRecord
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.UploadFile

interface MedicalRecordService {

    // Creates the record and uploads its files in one multipart request. Files are optional.
    suspend fun createMedicalRecord(
        record: NewMedicalRecord,
        files: List<UploadFile>
    ): Result<CreatedMedicalRecord, DataError.Remote>

}
