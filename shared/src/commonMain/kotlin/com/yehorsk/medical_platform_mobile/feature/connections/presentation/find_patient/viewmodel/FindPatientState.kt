package com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_patient.viewmodel

import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.PatientHasDoctorWithoutDoctor

data class FindPatientState(
    val isLoading: Boolean = false,
    val isConnected: Boolean = true,
    val patients: List<PatientHasDoctorWithoutDoctor> = listOf()
)
