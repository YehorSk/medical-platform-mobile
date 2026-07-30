package com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.viewmodel

import com.yehorsk.medical_platform_mobile.core.domain.model.Specialization

sealed interface FindDoctorAction {

    data class OnSearchTextChanged(val value: String): FindDoctorAction

    data class OnCityTextChanged(val value: String): FindDoctorAction

    data class OnSpecializationClicked(val item: Specialization): FindDoctorAction

    data class OnDoctorClicked(val id: String): FindDoctorAction

    data object ShowFilterBottomSheet: FindDoctorAction

    data object OnApplyFiltersClicked: FindDoctorAction

    data object OnLoadNextPage: FindDoctorAction

    data object ShowMyDoctors: FindDoctorAction

}