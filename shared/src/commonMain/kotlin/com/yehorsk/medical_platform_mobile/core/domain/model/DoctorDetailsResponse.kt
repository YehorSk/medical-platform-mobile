package com.yehorsk.medical_platform_mobile.core.domain.model

data class DoctorDetailsResponse(
    val doctor: Doctor,
    val access: PatientHasDoctor? = null
)
