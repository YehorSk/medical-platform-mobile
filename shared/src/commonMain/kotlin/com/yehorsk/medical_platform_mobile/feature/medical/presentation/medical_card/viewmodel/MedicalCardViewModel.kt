package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_card.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MedicalCardViewModel(
) : ViewModel() {

    private val _state = MutableStateFlow(MedicalCardState())
    val state: StateFlow<MedicalCardState> = _state.asStateFlow()

    fun onAction(action: MedicalCardAction) {
        when (action) {
            is MedicalCardAction.GenderSelected -> {
                _state.update { state ->
                    state.copy(
                        form = state.form.copy(
                            gender = action.gender
                        )
                    )
                }
            }

            is MedicalCardAction.BloodTypeSelected -> {
                _state.update { state ->
                    state.copy(
                        form = state.form.copy(
                            bloodType = action.bloodType
                        )
                    )
                }
            }

            is MedicalCardAction.InsuranceProviderChanged -> {
                _state.update { state ->
                    state.copy(
                        form = state.form.copy(
                            insuranceProvider = action.value
                        )
                    )
                }
            }

            is MedicalCardAction.InsurancePolicyMemberIdChanged -> {
                _state.update { state ->
                    state.copy(
                        form = state.form.copy(
                            insurancePolicyMemberId = action.value
                        )
                    )
                }
            }

            is MedicalCardAction.BirthOfDateChanged -> {
                _state.update { state ->
                    state.copy(
                        form = state.form.copy(
                            dateOfBirth = action.date
                        )
                    )
                }
            }

            MedicalCardAction.HideSaveConfirmationDialog -> {
                _state.update {
                    it.copy(
                        showUpdateConfirmation = false
                    )
                }
            }
            MedicalCardAction.ShowSaveConfirmationDialog -> {
                _state.update {
                    it.copy(
                        showUpdateConfirmation = true
                    )
                }
            }

            MedicalCardAction.OnSaveClicked -> {
                saveMedicalCard()
            }
            MedicalCardAction.OnGoBackClicked -> {}
        }
    }

    private fun saveMedicalCard() {
        val form = _state.value.form
        _state.update {
            it.copy(
                showUpdateConfirmation = false
            )
        }

//        viewModelScope.launch {
//            _state.update {
//                it.copy(
//                    isSaving = true,
//                    error = null
//                )
//            }
//
//            try {
//                // saveMedicalCardUseCase(form)
//
//                _state.update {
//                    it.copy(isSaving = false)
//                }
//            } catch (e: Exception) {
//                _state.update {
//                    it.copy(
//                        isSaving = false,
//                        error = e.message
//                    )
//                }
//            }
//        }
    }
}