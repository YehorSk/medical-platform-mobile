package com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.viewmodel

import com.yehorsk.medical_platform_mobile.core.domain.model.Doctor

data class DoctorDetailsState(
    val isLoading: Boolean = false,
    val isLoadingDoctor: Boolean = false,
    val doctorId: String = "",
    val doctor: Doctor? = null,
    val isConnected: Boolean = true,
)
