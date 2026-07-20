package com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.domain.service.DoctorService
import com.yehorsk.medical_platform_mobile.core.domain.service.SpecializationService
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@OptIn(FlowPreview::class)
class FindDoctorViewModel(
    private val specializationService: SpecializationService,
    private val doctorService: DoctorService,
    private val mainLogger: MainLogger
): ViewModel() {

    private var hasLoadedInitialData = false

    private val _uiState = MutableStateFlow(FindDoctorState())
    val uiState = _uiState
        .onStart {
            if(!hasLoadedInitialData){
                searchFlow.launchIn(viewModelScope)
                getAllSpecializations()
                getAllDoctors()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = FindDoctorState()
        )

    private val searchFlow = _uiState
        .map { it.form.search }
        .distinctUntilChanged()
        .drop(1)
        .debounce(1.seconds)
        .onEach {
            getAllDoctors()
        }

    fun onAction(action: FindDoctorAction){
        when(action){
            is FindDoctorAction.OnSearchTextChanged -> {
                _uiState.update { it.copy(form = it.form.copy(search = action.value)) }
            }
            FindDoctorAction.ShowFilterBottomSheet -> {
                _uiState.update { it.copy(showFilterBottomSheet = !it.showFilterBottomSheet) }
            }
            is FindDoctorAction.OnCityTextChanged -> {
                _uiState.update { it.copy(form = it.form.copy(city = action.value)) }
            }
            is FindDoctorAction.OnSpecializationClicked -> {
                _uiState.update { state ->
                    val selected = state.form.specializations

                    state.copy(
                        form = state.form.copy(
                            specializations =
                                if (selected.any{ it.id == action.item.id}) {
                                    selected - action.item
                                } else {
                                    selected + action.item
                                }
                        )
                    )
                }
            }
            FindDoctorAction.OnApplyFiltersClicked -> {
                _uiState.update { it.copy(showFilterBottomSheet = false) }
                getAllDoctors()
            }
        }
    }

    private fun getAllSpecializations(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingSpecialization = true
                )
            }
            specializationService
                .getAll()
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            specializations = response.data,
                            isLoadingSpecialization = false
                        )
                    }
                }
                .onFailure { dataErrorRemote ->
                    _uiState.update { it.copy(isLoadingSpecialization = false) }
                    SnackbarController.sendEvent(SnackbarEvent(error = dataErrorRemote))
                }
        }
    }

    private fun getAllDoctors(){
        viewModelScope.launch {
            _uiState.update {
                it.copy( isLoadingDoctors = true )
            }
            doctorService
                .getDoctors(
                    request = _uiState.value.form,
                    page = 0,
                    size = 10
                )
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            doctors = response.content,
                            isLoadingDoctors = false
                        )
                    }
                    mainLogger.debug("Doctors response: $response")
                }
                .onFailure { dataErrorRemote ->
                    _uiState.update { it.copy(isLoadingDoctors = false) }
                    SnackbarController.sendEvent(SnackbarEvent(error = dataErrorRemote))
                }
        }
    }

}