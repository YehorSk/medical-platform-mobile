package com.yehorsk.medical_platform_mobile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.yehorsk.medical_platform_mobile.core.domain.repository.SessionStorage
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.auth.data.dto.RefreshTokenDto
import com.yehorsk.medical_platform_mobile.feature.auth.data.mappers.toAuthDataDto
import com.yehorsk.medical_platform_mobile.feature.auth.domain.AuthService
import com.yehorsk.medical_platform_mobile.util.getRole
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val sessionStorage: SessionStorage,
    private val authService: AuthService
): ViewModel() {

    private val eventChannel = Channel<MainEvent>()
    val events = eventChannel.receiveAsFlow()

    private var hasLoadedInitialData = false

    private val _uiState = MutableStateFlow(MainState())
    val uiState = _uiState
        .onStart {
            if(!hasLoadedInitialData){
                authenticate()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MainState()
        )

    fun authenticate() {
        Logger.withTag("MainViewModel").i { "authenticate" }
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isCheckingAuth = true,
                    isLoading = true
                )
            }
            authService
                .me()
                .onSuccess { data, _ ->
                    Logger.withTag("MainViewModel").i { "onSuccess" }
                    _uiState.update {
                        it.copy(
                            isCheckingAuth = false,
                            isLoggedIn = true,
                            isLoading = false,
                            userRole = data.getUserRole()
                        )
                    }
                }
                .onFailure { dataErrorRemote ->
                    Logger.withTag("MainViewModel").i { "onFailure - $dataErrorRemote" }
                    _uiState.update {
                        sessionStorage.clearAuthData()
                        it.copy(
                            isCheckingAuth = false,
                            isLoggedIn = false,
                            isLoading = false
                        )
                    }
                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            error = dataErrorRemote
                        )
                    )
                    eventChannel.send(MainEvent.OnSessionExpired)
                }
        }
    }

}