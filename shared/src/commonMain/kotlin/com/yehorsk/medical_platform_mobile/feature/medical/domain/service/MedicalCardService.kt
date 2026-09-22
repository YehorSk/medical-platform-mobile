package com.yehorsk.medical_platform_mobile.feature.medical.domain.service

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.response.MedicalCard
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_card.viewmodel.MedicalCardForm

interface MedicalCardService {

    suspend fun getPatientById(patientId: String): Result<ApiResponseWithData<MedicalCard>, DataError.Remote>

    suspend fun getMyMedicalCard(): Result<ApiResponseWithData<MedicalCard>, DataError.Remote>

    suspend fun updateMyMedicalCard(form: MedicalCardForm): Result<ApiResponseWithData<MedicalCard>, DataError.Remote>

}