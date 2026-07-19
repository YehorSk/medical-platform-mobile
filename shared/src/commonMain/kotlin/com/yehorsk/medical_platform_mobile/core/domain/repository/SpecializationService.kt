package com.yehorsk.medical_platform_mobile.core.domain.repository

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.domain.model.Specialization
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result

interface SpecializationService {

    suspend fun getAll(): Result<ApiResponseWithData<List<Specialization>>, DataError.Remote>

}