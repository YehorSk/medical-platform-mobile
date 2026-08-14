package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.viewmodel

import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment

data class AppointmentDetailsState(
    val isLoading: Boolean = false,
    val isConnected: Boolean = false,
    val appointment: Appointment? = null
)
