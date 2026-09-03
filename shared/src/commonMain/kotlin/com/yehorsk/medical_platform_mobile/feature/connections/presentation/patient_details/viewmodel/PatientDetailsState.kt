package com.yehorsk.medical_platform_mobile.feature.connections.presentation.patient_details.viewmodel

import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.MedicalCard

data class PatientDetailsState(
    val isLoading: Boolean = false,
    val patientId: String = "",
    val patient: MedicalCard? = null,
    val isConnected: Boolean = true,
)
