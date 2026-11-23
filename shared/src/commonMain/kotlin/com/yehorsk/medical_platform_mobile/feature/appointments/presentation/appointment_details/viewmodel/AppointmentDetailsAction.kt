package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.viewmodel

import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentStatus

sealed interface AppointmentDetailsAction {

    data object OnGoBackClicked: AppointmentDetailsAction

    data object OnRescheduleClicked: AppointmentDetailsAction

    data object OnCreateMedicalRecordClicked: AppointmentDetailsAction

    data object ShowBottomSheet: AppointmentDetailsAction

    /** Opens the confirmation dialog; nothing is sent until [OnStatusChangeConfirmed]. */
    data class OnStatusChangeRequested(val status: AppointmentStatus): AppointmentDetailsAction

    data object OnStatusChangeConfirmed: AppointmentDetailsAction

    data object OnStatusChangeDismissed: AppointmentDetailsAction

}
