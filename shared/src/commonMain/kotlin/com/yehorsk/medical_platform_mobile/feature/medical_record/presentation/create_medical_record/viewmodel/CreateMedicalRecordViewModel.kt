package com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record.viewmodel

import androidx.lifecycle.ViewModel
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel.RegisterState
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyHitRegion
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyRegion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateMedicalRecordViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CreateRecordScreenState())
    val uiState: StateFlow<CreateRecordScreenState> = _uiState.asStateFlow()

    fun onAction(action: CreateRecordAction) {
        when (action) {
            is CreateRecordAction.OnBodyPartSelected -> onBodyPartSelected(action.part)
            is CreateRecordAction.OnBodyRegionSelected -> onBodyRegionSelected(action.region)
            is CreateRecordAction.OnTabSelected -> onTabSelected(action.tab)
            CreateRecordAction.OnGoBackClicked -> {}
        }
    }

    private fun onBodyPartSelected(part: BodyHitRegion) {
        _uiState.update { state ->
            val selectedParts = state.form.selectedBodyParts

            state.copy(
                form = state.form.copy(
                    selectedBodyParts = if (part in selectedParts) {
                        selectedParts - part
                    } else {
                        selectedParts + part
                    }
                )
            )
        }
    }

    private fun onBodyRegionSelected(region: BodyRegion) {
        _uiState.update { state ->
            state.copy(
                selectedRegion = region
            )
        }
    }

    private fun onTabSelected(tab: CreateRecordTab) {
        _uiState.update { state ->
            state.copy(
                currentTab = tab
            )
        }
    }
}