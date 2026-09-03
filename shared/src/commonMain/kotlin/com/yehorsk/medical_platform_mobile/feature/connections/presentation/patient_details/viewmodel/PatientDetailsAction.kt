package com.yehorsk.medical_platform_mobile.feature.connections.presentation.patient_details.viewmodel

sealed interface PatientDetailsAction {

    data class OnGetPatientById(val id: String): PatientDetailsAction

    data object GoBackClicked: PatientDetailsAction

}