package com.yehorsk.medical_platform_mobile.feature.settings.presentation.doctor_schedule.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.domain.service.ScheduleService
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.schedule_updated
import org.jetbrains.compose.resources.getString

class DoctorScheduleViewModel(
    private val scheduleService: ScheduleService
): ViewModel() {

    private val _state = MutableStateFlow(DoctorScheduleUiState())
    val state: StateFlow<DoctorScheduleUiState> = _state.asStateFlow()

    init {
        loadSchedule()
    }

    fun onAction(action: DoctorScheduleAction) {
        when (action) {
            is DoctorScheduleAction.DayChanged -> updateDay(action.day)
            is DoctorScheduleAction.CopyToWeekdays -> copyToWeekdays(action.source)
            DoctorScheduleAction.OnSaveClicked -> save()
            DoctorScheduleAction.OnGoBackClicked -> {}
        }
    }

    private fun loadSchedule() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            scheduleService
                .getMySchedule()
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            schedule = response.data.daySchedule.toWeekUiState(),
                            isLoading = false
                        )
                    }
                }.onFailure { error ->
                    _state.update { it.copy(isLoading = false) }
                    SnackbarController.sendEvent(SnackbarEvent(error = error))
                }
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
        if (_state.value.isSaving) return
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            scheduleService
                .updateMySchedule(_state.value.schedule.toUpdateScheduleRequest())
                .onSuccess {
                    _state.update { it.copy(isSaving = false) }
                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            message = getString(UiRes.string.schedule_updated)
                        )
                    )
                }.onFailure { error ->
                    _state.update { it.copy(isSaving = false) }
                    SnackbarController.sendEvent(SnackbarEvent(error = error))
                }
        }
    }

}
