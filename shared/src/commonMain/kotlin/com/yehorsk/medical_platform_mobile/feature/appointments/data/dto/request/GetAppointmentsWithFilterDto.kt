package com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.request

import kotlinx.serialization.Serializable

// from/to are ISO-8601 instants, statuses and sortDirection are enum names
@Serializable
data class GetAppointmentsWithFilterDto(
    val statuses: List<String>? = null,
    val doctorId: String? = null,
    val patientId: String? = null,
    val from: String? = null,
    val to: String? = null,
    val upcoming: Boolean = false,
    val sortDirection: String = "ASC"
)
