package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.viewmodel

import kotlinx.datetime.LocalDate

sealed interface AppointmentsListAction {

    data object OnGoBackClicked: AppointmentsListAction

    data class OnAppointmentClicked(val appointmentId: String): AppointmentsListAction

    // Sent once the doctor calendar is on screen, so the schedule is only fetched when needed
    data object OnCalendarShown: AppointmentsListAction

    data object OnRefreshClicked: AppointmentsListAction

    data class OnViewModeChanged(val mode: AppointmentsViewMode): AppointmentsListAction

    data class OnCalendarModeChanged(val mode: CalendarMode): AppointmentsListAction

    data class OnSelectedDateChanged(val date: LocalDate): AppointmentsListAction

    // Clicking a day in the month view opens that day
    data class OnDayClicked(val date: LocalDate): AppointmentsListAction

}
