package com.yehorsk.medical_platform_mobile.core.domain.service

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.PagedResponseDto
import com.yehorsk.medical_platform_mobile.core.domain.model.Doctor
import com.yehorsk.medical_platform_mobile.core.domain.model.DoctorDetailsResponse
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.viewmodel.GetDoctorsWithFilter

interface DoctorService {

    suspend fun getDoctors(request: GetDoctorsWithFilter, page: Int, size: Int): Result<PagedResponseDto<Doctor>, DataError.Remote>

    suspend fun getDoctorById(id: String): Result<ApiResponseWithData<DoctorDetailsResponse>, DataError.Remote>

}