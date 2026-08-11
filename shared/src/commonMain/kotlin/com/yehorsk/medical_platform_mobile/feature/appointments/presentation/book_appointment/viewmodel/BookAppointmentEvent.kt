package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.viewmodel

sealed interface BookAppointmentEvent {
    data object NavigateBack : BookAppointmentEvent
    data object Success: BookAppointmentEvent
}