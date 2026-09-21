package com.yehorsk.medical_platform_mobile.feature.medical.domain.models

data class MedicalRecordPatient(
    val id: String,
    val firstName: String,
    val lastName: String,
    val title: String
) {
    val fullName: String
        get() = listOf(title, firstName, lastName)
            .filter { it.isNotBlank() }
            .joinToString(" ")
}