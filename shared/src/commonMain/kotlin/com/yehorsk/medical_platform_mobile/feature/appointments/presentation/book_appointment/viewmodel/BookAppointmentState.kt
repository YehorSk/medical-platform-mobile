package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.viewmodel

import com.kizitonwose.calendar.core.now
import com.yehorsk.medical_platform_mobile.core.domain.model.Doctor
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

data class BookAppointmentState(
    val currentStep: BookingStep = BookingStep.Date,
    val doctor: Doctor? = null,
    val isLoading: Boolean = false,
    val isConnected: Boolean = false,
    val form: BookingForm = BookingForm(),
    val openWeekDays: List<DayOfWeek?> = emptyList(),
    val availableTime: List<String> = listOf("10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30")
)

@Serializable
data class BookingForm(
    val selectedDate: String = LocalDate.now().toString(),
    val selectedTime: String? = null,
    val note: String = ""
)

enum class BookingStep {
    Doctor,
    Date,
    Time,
    Confirm
}