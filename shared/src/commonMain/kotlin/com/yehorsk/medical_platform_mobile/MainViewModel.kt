package com.yehorsk.medical_platform_mobile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.domain.repository.SessionStorage
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.auth.data.mappers.toAuthDataDto
import com.yehorsk.medical_platform_mobile.feature.auth.domain.AuthService
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
                observeAuthData()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MainState()
        )

    init {
        viewModelScope.launch {
            val authInfo = sessionStorage.observeAuthData().firstOrNull()
            _uiState.update { it.copy(
                isCheckingAuth = false,
                isLoggedIn = authInfo != null
            ) }
        }
    }

    private var previousRefreshToken: String? = null
//    private var currentDeviceToken: String? = null
//    private var previousDeviceToken: String? = null

    fun observeAuthData() {
        sessionStorage
            .observeAuthData()
            .onEach { authData ->
                val currentRefreshToken = authData?.refreshToken
                val isSessionExpired = previousRefreshToken != null && currentRefreshToken == null
                if(isSessionExpired){
                    sessionStorage.clearAuthData()
                    _uiState.update {
                        it.copy(
                            isLoggedIn = false
                        )
                    }
                    eventChannel.send(MainEvent.OnSessionExpired)
                }
                authData?.let { data ->
                    _uiState.update {
                        it.copy(
                            isLoggedIn = true,
                            userRole = data.user.getUserRole(),
                            userId = data.user.id
                        )
                    }
                }
                previousRefreshToken = currentRefreshToken
            }
            .launchIn(viewModelScope)
    }

}