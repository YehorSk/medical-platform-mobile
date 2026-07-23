package com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.viewmodel

sealed interface DoctorDetailsAction {

    data class OnGetDoctorById(val id: String): DoctorDetailsAction

    data object GoBackClicked: DoctorDetailsAction

    data object OnGrantAccessClicked: DoctorDetailsAction

    data object OnOpenChatClicked: DoctorDetailsAction

    data object OnRevokeAccessClicked: DoctorDetailsAction

    data object OnApproveAccessClicked: DoctorDetailsAction

    data object OnDeclineAccessClicked: DoctorDetailsAction

    data object OnBookAppointmentClicked: DoctorDetailsAction



}