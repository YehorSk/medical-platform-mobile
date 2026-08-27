package com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response

data class MedicalCard(
    val id: String,
    val bloodType: String,
    val insuranceNumber: String?,
    val patient: MedicalCardPatient?,
    val createdAt: String,
    val updatedAt: String
)