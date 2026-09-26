package com.yehorsk.medical_platform_mobile.feature.settings.presentation.doctor_schedule.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DoctorScheduleViewModel: ViewModel() {

    private val _state = MutableStateFlow(DoctorScheduleUiState())
    val state: StateFlow<DoctorScheduleUiState> = _state.asStateFlow()

    fun onAction(action: DoctorScheduleAction) {
        when (action) {
            is DoctorScheduleAction.DayChanged -> updateDay(action.day)
            is DoctorScheduleAction.CopyToWeekdays -> copyToWeekdays(action.source)
            DoctorScheduleAction.OnSaveClicked -> save()
            DoctorScheduleAction.OnGoBackClicked -> {}
        }
    }

    private fun updateDay(updated: DayScheduleUiState) {
        _state.update { current ->
            current.copy(schedule = current.schedule.map { if (it.weekDay == updated.weekDay) updated else it })
        }
    }

    private fun copyToWeekdays(source: DayScheduleUiState) {
        _state.update { current ->
            current.copy(
                schedule = current.schedule.map { day ->
                    if (day.weekDay in WEEKDAYS && day.weekDay != source.weekDay) {
                        day.copy(
                            isWorkingDay = source.isWorkingDay,
                            startTime = source.startTime,
                            endTime = source.endTime,
                            slotDurationMinutes = source.slotDurationMinutes,
                            hasBreak = source.hasBreak,
                            lunchStart = source.lunchStart,
                            lunchEnd = source.lunchEnd,
                        )
                    } else day
                },
            )
        }
    }

    private fun save() {
        viewModelScope.launch {

        }
    }

}