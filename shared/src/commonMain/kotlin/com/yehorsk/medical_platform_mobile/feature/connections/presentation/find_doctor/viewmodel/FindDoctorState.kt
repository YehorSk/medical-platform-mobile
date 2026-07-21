package com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.viewmodel

import com.yehorsk.medical_platform_mobile.core.domain.model.Doctor
import com.yehorsk.medical_platform_mobile.core.domain.model.Specialization

data class FindDoctorState(
    val isLoading: Boolean = false,
    val isLoadingSpecialization: Boolean = false,
    val isConnected: Boolean = true,
    val isLoadingDoctors: Boolean = false,
    val showFilterBottomSheet: Boolean = false,
    val specializations: List<Specialization> = listOf(),
    val doctors: List<Doctor> = listOf(),
    val form: GetDoctorsWithFilter = GetDoctorsWithFilter()
)

data class GetDoctorsWithFilter(
    val search: String = "",
    val specializations: Set<Specialization> = setOf(),
    val city: String = ""
)