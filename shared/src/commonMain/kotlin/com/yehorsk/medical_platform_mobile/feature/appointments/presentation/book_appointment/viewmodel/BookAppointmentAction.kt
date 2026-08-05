package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.viewmodel

sealed interface BookAppointmentAction {

    data class OnDateSelected(val date: String): BookAppointmentAction

    data class OnTimeSelected(val time: String): BookAppointmentAction

    data class OnNoteChanged(val note: String): BookAppointmentAction

    data class OnGoToNextStateClicked(val step: BookingStep): BookAppointmentAction

    data object OnGoBackClicked: BookAppointmentAction

}