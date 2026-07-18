package com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class FindDoctorViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(FindDoctorState())
    val uiState = _uiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = FindDoctorState()
        )

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
                    val selected = state.form.selectedSpecializations

                    state.copy(
                        form = state.form.copy(
                            selectedSpecializations =
                                if (selected.any{ it.id == action.item.id}) {
                                    selected - action.item
                                } else {
                                    selected + action.item
                                }
                        )
                    )
                }
            }
            FindDoctorAction.OnApplyFiltersClicked -> {}
        }
    }

}