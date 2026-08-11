package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.data.mappers.toDayOfWeek
import com.yehorsk.medical_platform_mobile.core.data.network.ConnectivityObserver
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.appointments.data.dto.request.CreateAppointmentRequestDto
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.AppointmentService
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.ScheduleService
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.mappers.toDayScheduleUi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class BookAppointmentViewModel(
    private val mainLogger: MainLogger,
    private val connectivityObserver: ConnectivityObserver,
    private val appointmentService: AppointmentService,
    private val scheduleService: ScheduleService,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var hasLoadedInitialData = false

    private val doctorId = savedStateHandle.get<String>("doctorId")
        ?: throw IllegalStateException("Doctor id is missing")

    private val _uiState = MutableStateFlow(BookAppointmentState())
    val uiState = _uiState
        .onStart {
            if(!hasLoadedInitialData){
                getWeekSchedule()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = BookAppointmentState()
        )


    private val eventChannel = Channel<BookAppointmentEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        observeConnectivity()
    }

    fun onAction(action: BookAppointmentAction){
        when(action){
            is BookAppointmentAction.OnDateSelected -> {
                onDateSelected(action.date)
            }
            is BookAppointmentAction.OnTimeSelected -> {
                onTimeSelected(action.time)
            }

            is BookAppointmentAction.OnNoteChanged -> {
                onNoteChanged(action.note)
            }

            is BookAppointmentAction.OnGoToNextStateClicked -> {
                onGoToNextStateClicked(action.step)
            }

            BookAppointmentAction.OnGoBackClicked -> {
                onGoBackClicked()
            }

            BookAppointmentAction.OnCreateAppointmentClicked -> {
                createAppointment()
            }
        }
    }

    private fun onNoteChanged(note: String) {
        _uiState.update { it.copy(
            form = it.form.copy(
                note = note
            )
        ) }
    }

    private fun onGoBackClicked() {
        when (_uiState.value.currentStep) {
            BookingStep.Date, BookingStep.Doctor -> {
                viewModelScope.launch {
                    eventChannel.send(BookAppointmentEvent.NavigateBack)
                }
            }

            BookingStep.Time -> {
                _uiState.update {
                    it.copy(currentStep = BookingStep.Date)
                }
            }

            BookingStep.Confirm -> {
                _uiState.update {
                    it.copy(currentStep = BookingStep.Time)
                }
            }
        }
    }

    private fun onDateSelected(date: String) {
        _uiState.update { it.copy(
            form = it.form.copy(
                selectedDate = date
            )
        ) }
    }

    private fun onTimeSelected(time: String) {
        _uiState.update { it.copy(
            form = it.form.copy(
                selectedTime = time
            )
        ) }
    }

    private fun createAppointment(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            appointmentService
                .createAppointment(CreateAppointmentRequestDto(
                    doctorId = uiState.value.form.doctorId!!,
                    date = uiState.value.form.selectedDate,
                    time = uiState.value.form.selectedTime!!,
                    note = uiState.value.form.note
                ))
                .onSuccess { response ->
                    SnackbarController.sendEvent(
                        SnackbarEvent(message = response.message)
                    )
                    _uiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    eventChannel.send(BookAppointmentEvent.Success)
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoading = false)
                    }

                    SnackbarController.sendEvent(
                        SnackbarEvent(error = error)
                    )
                }
        }
    }

    private fun onGoToNextStateClicked(step: BookingStep) {
        when(step){
            BookingStep.Doctor -> {}
            BookingStep.Date -> {}
            BookingStep.Time -> { getDateAvailableTimes() }
            BookingStep.Confirm -> {}
        }
        _uiState.update { it.copy(
            currentStep = step
        ) }
    }

    private fun observeConnectivity() {
        connectivityObserver.isConnected
            .distinctUntilChanged()
            .onEach { connected ->
                mainLogger.debug("Connectivity = $connected")
                _uiState.update { it.copy(isConnected = connected) }
            }
            .launchIn(viewModelScope)
    }

    private fun getWeekSchedule() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoadingDates = true)
            }

            scheduleService
                .getSchedule(doctorId)
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            isLoadingDates = false,
                            doctor = response.data.doctor,
                            form = it.form.copy(doctorId = response.data.doctor.id),
                            openWeekDays = response.data.daySchedule.map { data ->
                                data.toDayScheduleUi()
                            }
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoadingDates = false)
                    }

                    SnackbarController.sendEvent(
                        SnackbarEvent(error = error)
                    )
                }
        }
    }

    private fun getDateAvailableTimes() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoadingTimes = true)
            }
            scheduleService
                .getScheduleAvailableTimes(doctorId, _uiState.value.form.selectedDate)
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            isLoadingTimes = false,
                            availableTime = response.data.availableTimes
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoadingTimes = false)
                    }

                    SnackbarController.sendEvent(
                        SnackbarEvent(error = error)
                    )
                }
        }
    }

}