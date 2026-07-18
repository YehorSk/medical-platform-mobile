package com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.viewmodel

import com.yehorsk.medical_platform_mobile.core.domain.model.Specialization

data class FindDoctorState(
    val isLoading: Boolean = false,
    val showFilterBottomSheet: Boolean = false,
    val specializations: List<Specialization> = listOf(
        Specialization("1", "Cardiology"),
        Specialization("2", "Dermatology"),
        Specialization("3", "Neurology"),
        Specialization("4", "Orthopedics"),
        Specialization("5", "Pediatrics"),
        Specialization("6", "Psychiatry"),
        Specialization("7", "Radiology"),
        Specialization("8", "Urology")
    ),
    val form: FindDoctorForm = FindDoctorForm()
)

data class FindDoctorForm(
    val search: String = "",
    val selectedSpecializations: Set<Specialization> = setOf(),
    val city: String = ""
)