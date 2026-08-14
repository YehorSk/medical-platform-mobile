package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.viewmodel

sealed interface AppointmentDetailsAction {

    data object OnGoBackClicked: AppointmentDetailsAction

    data object OnCancelClicked: AppointmentDetailsAction

    data object OnRescheduleClicked: AppointmentDetailsAction

}