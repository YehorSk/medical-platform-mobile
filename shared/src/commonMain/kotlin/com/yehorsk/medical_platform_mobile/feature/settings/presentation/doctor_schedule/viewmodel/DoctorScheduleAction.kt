package com.yehorsk.medical_platform_mobile.feature.settings.presentation.doctor_schedule.viewmodel

sealed interface DoctorScheduleAction {
    data class DayChanged(val day: DayScheduleUiState) : DoctorScheduleAction
    data class CopyToWeekdays(val source: DayScheduleUiState) : DoctorScheduleAction
    data object OnSaveClicked : DoctorScheduleAction
    data object OnGoBackClicked : DoctorScheduleAction
}