package com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.viewmodel

import com.yehorsk.medical_platform_mobile.core.domain.model.Specialization

data class FindDoctorState(
    val isLoading: Boolean = false,
    val isLoadingSpecialization: Boolean = false,
    val showFilterBottomSheet: Boolean = false,
    val specializations: List<Specialization> = listOf(),
    val form: FindDoctorForm = FindDoctorForm()
)

data class FindDoctorForm(
    val search: String = "",
    val selectedSpecializations: Set<Specialization> = setOf(),
    val city: String = ""
)