package com.yehorsk.medical_platform_mobile.core.data.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class DoctorDetailsResponseDto(
    val doctor: DoctorResponseDto,
    val access: PatientHasDoctorDto? = null
)
