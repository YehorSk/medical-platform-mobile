package com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.viewmodel

sealed interface DoctorDetailsAction {

    data class OnGetDoctorById(val id: String): DoctorDetailsAction

    data object GoBackClicked: DoctorDetailsAction

}