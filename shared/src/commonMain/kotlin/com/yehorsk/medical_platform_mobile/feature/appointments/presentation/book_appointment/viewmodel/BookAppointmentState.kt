package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.viewmodel

import com.kizitonwose.calendar.core.now
import com.yehorsk.medical_platform_mobile.core.domain.model.DaySchedule
import com.yehorsk.medical_platform_mobile.core.domain.model.Doctor
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.model.DayScheduleUi
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class BookAppointmentState(
    val currentStep: BookingStep = BookingStep.Date,
    val doctor: Doctor? = null,
    val isLoading: Boolean = false,
    val isLoadingDates: Boolean = false,
    val isLoadingTimes: Boolean = false,
    val isConnected: Boolean = false,
    val form: BookingForm = BookingForm(),
    val openWeekDays: List<DayScheduleUi> = emptyList(),
    val availableTime: List<String> = emptyList()
)

@Serializable
data class BookingForm(
    val doctorId: String? = null,
    @SerialName("date")
    val selectedDate: String = LocalDate.now().toString(),
    @SerialName("time")
    val selectedTime: String? = null,
    val note: String = ""
)

enum class BookingStep {
    Doctor,
    Date,
    Time,
    Confirm
}