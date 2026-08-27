package com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response

import com.yehorsk.medical_platform_mobile.core.domain.model.AccessStatus

data class PatientHasDoctorWithoutDoctor(
    val id: String,
    val medicalCard: MedicalCard,
    val status: AccessStatus,
    val createdAt: String
)