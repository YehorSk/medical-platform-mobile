package com.yehorsk.medical_platform_mobile.core.data.network

import com.yehorsk.medical_platform_mobile.core.data.mappers.toDoctor
import com.yehorsk.medical_platform_mobile.core.data.mappers.toGetDoctorsWithFilterDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.request.GetDoctorsWithFilterDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.DoctorResponseDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.PagedResponseDto
import com.yehorsk.medical_platform_mobile.core.domain.model.Doctor
import com.yehorsk.medical_platform_mobile.core.domain.service.DoctorService
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.core.util.map
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.viewmodel.GetDoctorsWithFilter
import io.ktor.client.HttpClient

class DoctorServiceImpl(
    private val httpClient: HttpClient
): DoctorService {

    override suspend fun getDoctors(request: GetDoctorsWithFilter, page: Int, size: Int): Result<PagedResponseDto<Doctor>, DataError.Remote> {
        return httpClient.post<GetDoctorsWithFilterDto, PagedResponseDto<DoctorResponseDto>>(
            body = request.toGetDoctorsWithFilterDto(),
            route = "/doctors/search",
            queryParams = mapOf(
                "page" to page,
                "size" to size
            )
        ).map { response ->
            PagedResponseDto(
                content = response.content.map { it.toDoctor() },
                page = response.page,
                size = response.size,
                totalElements = response.totalElements,
                totalPages = response.totalPages,
                hasNext = response.hasNext
            )
        }
    }

    override suspend fun getDoctorById(id: String): Result<Doctor, DataError.Remote> {
        return httpClient.get<DoctorResponseDto>(
            route = "/doctors/get-doctor",
            queryParams = mapOf(
                "doctorId" to id
            )
        ).map { it.toDoctor() }
    }

}