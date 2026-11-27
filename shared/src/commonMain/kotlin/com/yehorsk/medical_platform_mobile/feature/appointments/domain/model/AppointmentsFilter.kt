package com.yehorsk.medical_platform_mobile.feature.appointments.domain.model

import kotlinx.datetime.LocalDate

data class AppointmentsFilter(
    val statuses: Set<AppointmentStatus> = emptySet(),
    val from: LocalDate? = null,
    val to: LocalDate? = null,
    val upcoming: Boolean = false,
    val sortDirection: SortDirection = SortDirection.ASC
) {
    // Sort order alone doesn't hide anything, so it doesn't count as an active filter
    val isActive: Boolean
        get() = statuses.isNotEmpty() || from != null || to != null || upcoming

    val hasInvalidRange: Boolean
        get() = from != null && to != null && from > to
}

enum class SortDirection { ASC, DESC }
