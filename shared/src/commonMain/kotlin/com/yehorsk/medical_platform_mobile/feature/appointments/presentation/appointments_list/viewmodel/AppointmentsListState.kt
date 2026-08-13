package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.viewmodel

import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment

data class AppointmentsListState(
    val appointments: List<Appointment> = emptyList(),
    val isConnected: Boolean = true,
    val isLoading: Boolean = false,
)
