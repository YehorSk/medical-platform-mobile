package com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.viewmodel

import com.yehorsk.medical_platform_mobile.core.domain.model.Doctor
import com.yehorsk.medical_platform_mobile.core.domain.model.PatientHasDoctor

data class DoctorDetailsState(
    val isLoading: Boolean = false,
    val isLoadingDoctor: Boolean = false,
    val doctorId: String = "",
    val doctorDetails: Doctor? = null,
    val patientAccess: PatientHasDoctor? = null,
    val isConnected: Boolean = true,
)
