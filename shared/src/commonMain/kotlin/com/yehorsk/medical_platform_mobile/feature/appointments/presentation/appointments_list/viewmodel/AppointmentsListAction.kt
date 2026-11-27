package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.viewmodel

import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentStatus
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.SortDirection
import kotlinx.datetime.LocalDate

sealed interface AppointmentsListAction {

    data object OnGoBackClicked: AppointmentsListAction

    data class OnAppointmentClicked(val appointmentId: String): AppointmentsListAction

    // Sent when the doctor calendar appears or disappears. The schedule is fetched only when
    // it's shown, and the calendar always loads appointments without list filters
    data class OnCalendarVisibilityChanged(val visible: Boolean): AppointmentsListAction

    data object OnRefreshClicked: AppointmentsListAction

    data class OnViewModeChanged(val mode: AppointmentsViewMode): AppointmentsListAction

    data class OnCalendarModeChanged(val mode: CalendarMode): AppointmentsListAction

    data class OnSelectedDateChanged(val date: LocalDate): AppointmentsListAction

    // Clicking a day in the month view opens that day
    data class OnDayClicked(val date: LocalDate): AppointmentsListAction

    data object OnFiltersClicked: AppointmentsListAction

    data object OnDismissFilters: AppointmentsListAction

    data class OnStatusClicked(val status: AppointmentStatus): AppointmentsListAction

    data class OnFromDateChanged(val date: LocalDate?): AppointmentsListAction

    data class OnToDateChanged(val date: LocalDate?): AppointmentsListAction

    data class OnUpcomingToggled(val upcoming: Boolean): AppointmentsListAction

    data class OnSortDirectionChanged(val direction: SortDirection): AppointmentsListAction

    data object OnClearFilters: AppointmentsListAction

    data object OnApplyFiltersClicked: AppointmentsListAction

}
