package com.yehorsk.medical_platform_mobile.core.data.network.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class GetDoctorsWithFilterDto(
    val search: String = "",
    val city: String = "",
    val specializations: List<String> = listOf(),
    val getPatientDoctors: Boolean = false
)
