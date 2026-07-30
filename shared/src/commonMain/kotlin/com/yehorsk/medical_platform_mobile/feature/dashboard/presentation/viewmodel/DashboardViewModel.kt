package com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.domain.repository.SessionStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class DashboardViewModel(
    private val sessionStorage: SessionStorage,
): ViewModel() {

    private val _uiState = MutableStateFlow(DashboardState())
    val uiState = _uiState
        .onStart {
            observeAuthData()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = DashboardState()
        )

    fun onAction(action: DashboardAction) = null

    private fun observeAuthData() {
        sessionStorage
            .observeAuthData()
            .onEach { authData ->
                authData?.user?.let { user ->
                    _uiState.update {
                        it.copy(
                            user = user,
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

}