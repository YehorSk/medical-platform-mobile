package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.viewmodel

import com.kizitonwose.calendar.core.now
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import kotlinx.datetime.LocalDate

data class BookAppointmentState(
    val currentStep: BookingStep = BookingStep.Date,
    val doctor: User? = null,
    val isLoading: Boolean = false,
    val isConnected: Boolean = false,
    val form: BookingForm = BookingForm()
)

data class BookingForm(
    val selectedDate: String = LocalDate.now().toString(),
)

enum class BookingStep {
    Date,
    Time,
    Confirm
}