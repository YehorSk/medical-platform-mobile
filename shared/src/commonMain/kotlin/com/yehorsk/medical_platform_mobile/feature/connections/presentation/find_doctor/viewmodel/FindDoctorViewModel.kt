package com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.data.network.ConnectivityObserver
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.domain.service.DoctorService
import com.yehorsk.medical_platform_mobile.core.domain.service.SpecializationService
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
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

private const val DOCTORS_PAGE_SIZE = 100

@OptIn(FlowPreview::class)
class FindDoctorViewModel(
    showMyDoctors: Boolean,
    private val specializationService: SpecializationService,
    private val doctorService: DoctorService,
    private val mainLogger: MainLogger,
    private val connectivityObserver: ConnectivityObserver
): ViewModel() {

    private var hasLoadedInitialData = false
    private var doctorsJob: Job? = null

    private val _uiState = MutableStateFlow(
        FindDoctorState(form = GetDoctorsWithFilter(showMyDoctors = showMyDoctors))
    )

    private val searchFlow = _uiState
        .map { it.form.search }
        .distinctUntilChanged()
        .drop(1)
        .debounce(1.seconds)
        .onEach {
            getAllDoctors()
        }

    val uiState = _uiState
        .onStart {
            if(!hasLoadedInitialData){
                searchFlow.launchIn(viewModelScope)
                getAllSpecializations()
                mainLogger.debug("Get Doctors: start")
                getAllDoctors()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = _uiState.value
        )

    init {
        observeConnectivity()
    }

    fun onAction(action: FindDoctorAction){
        when(action){
            is FindDoctorAction.OnSearchTextChanged -> {
                _uiState.update { it.copy(form = it.form.copy(search = action.value)) }
            }
            FindDoctorAction.OnFiltersClicked -> {
                _uiState.update { it.copy(showFilterBottomSheet = true, filterDraft = it.form) }
            }
            FindDoctorAction.OnDismissFilters -> {
                _uiState.update { it.copy(showFilterBottomSheet = false) }
            }
            is FindDoctorAction.OnCityTextChanged -> {
                _uiState.update { it.copy(filterDraft = it.filterDraft.copy(city = action.value)) }
            }
            is FindDoctorAction.OnSpecializationClicked -> {
                _uiState.update { state ->
                    val selected = state.filterDraft.specializations

                    state.copy(
                        filterDraft = state.filterDraft.copy(
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
                _uiState.update {
                    it.copy(
                        showFilterBottomSheet = false,
                        form = it.form.copy(
                            city = it.filterDraft.city,
                            specializations = it.filterDraft.specializations
                        )
                    )
                }
                getAllDoctors()
            }
            is FindDoctorAction.OnDoctorClicked -> {}
        }
    }

    private fun observeConnectivity() {
        connectivityObserver.isConnected
            .debounce(1.seconds)
            .distinctUntilChanged()
            .drop(1)
            .onEach { connected ->
                mainLogger.debug("Connectivity = $connected")
                _uiState.update { it.copy(isConnected = connected) }
                if(connected) {
                    mainLogger.debug("Get Doctors: wifi")
                    getAllDoctors()
                }
            }
            .launchIn(viewModelScope)
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
        // Cancel the previous request so a stale response can't overwrite newer results
        doctorsJob?.cancel()
        doctorsJob = viewModelScope.launch {
            _uiState.update {
                it.copy( isLoadingDoctors = true )
            }
            doctorService
                .getDoctors(
                    request = _uiState.value.form,
                    page = 0,
                    size = DOCTORS_PAGE_SIZE
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
