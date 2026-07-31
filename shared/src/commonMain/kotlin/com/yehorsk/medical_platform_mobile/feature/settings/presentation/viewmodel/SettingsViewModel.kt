package com.yehorsk.medical_platform_mobile.feature.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.domain.repository.SessionStorage
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.auth.data.mappers.toAuthDataDto
import com.yehorsk.medical_platform_mobile.feature.auth.domain.AuthService
import com.yehorsk.medical_platform_mobile.feature.settings.domain.SettingsService
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

class SettingsViewModel(
    private val sessionStorage: SessionStorage,
    private val authService: AuthService,
    private val settingsService: SettingsService
): ViewModel() {

    private val eventChannel = Channel<SettingsScreenEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _uiState = MutableStateFlow(SettingsState())
    val uiState = _uiState
        .onStart {
            observeAuthData()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SettingsState()
        )

    fun onAction(action: SettingsAction){
        when(action){
            SettingsAction.OnSaveDataClicked -> { saveData() }
            is SettingsAction.UpdateAddress -> { updateAddress(action.value) }
            is SettingsAction.UpdateEmergencyContactName -> { updateEmergencyContactName(action.value) }
            is SettingsAction.UpdateEmergencyContactPhone -> { updateEmergencyContactPhone(action.value) }
            is SettingsAction.UpdateFirstName -> { updateFirstName(action.value) }
            is SettingsAction.UpdatePhone -> { updatePhone(action.value) }
            is SettingsAction.UpdateSecondName -> { updateSecondName(action.value) }
            is SettingsAction.UpdateTitle -> { updateTitle(action.value) }
            SettingsAction.GoToProfileScreen -> {}
            SettingsAction.GoToUpdatePwdScreen -> {}
            SettingsAction.OnGoBackClicked -> {}
            SettingsAction.OnLogoutClicked -> { logout() }
        }
    }

    private fun observeAuthData() {
        sessionStorage
            .observeAuthData()
            .onEach { authData ->
                authData?.user?.let { user ->
                    _uiState.update {
                        it.copy(
                            user = user,
                            form = ProfileForm(
                                firstName = user.firstName,
                                lastName = user.lastName,
                                email = user.email,
                                phone = user.phone,
                                title = user.title,
                                address = user.address,
                                emergencyContactName = user.emergencyContactName,
                                emergencyContactPhone = user.emergencyContactPhone,
                            )
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun updateEmergencyContactName(value: String) {
        _uiState.update { it.copy(form = it.form.copy(emergencyContactName = value)) }
    }

    private fun updateEmergencyContactPhone(value: String) {
        _uiState.update { it.copy(form = it.form.copy(emergencyContactPhone = value)) }
    }

    private fun updatePhone(value: String) {
        _uiState.update { it.copy(form = it.form.copy(phone = value)) }
    }

    private fun updateSecondName(value: String) {
        _uiState.update { it.copy(form = it.form.copy(lastName = value)) }
    }

    private fun updateTitle(value: String) {
        _uiState.update { it.copy(form = it.form.copy(title = value)) }
    }

    private fun updateFirstName(value: String) {
        _uiState.update { it.copy(form = it.form.copy(firstName = value)) }
    }

    private fun updateAddress(value: String) {
        _uiState.update { it.copy(form = it.form.copy(address = value)) }
    }

    private fun logout() {
        viewModelScope.launch {
            _uiState.update { it.copy(
                isLoading = true
            ) }
            authService
                .logout()
                .onSuccess { response ->
                    sessionStorage.clearAuthData()
                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            message = response.message
                        )
                    )
                    eventChannel.send(SettingsScreenEvent.OnLogoutSuccess)
                }
                .onFailure {  dataErrorRemote ->
                    _uiState.update { it.copy(
                        isLoading = false
                    ) }
                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            error = dataErrorRemote
                        )
                    )

                }
        }
    }

    private fun saveData() {
        viewModelScope.launch {
            val current = sessionStorage.observeAuthData().firstOrNull()
            _uiState.update { it.copy(
                isLoading = true
            ) }
            settingsService
                .updateUserData(_uiState.value.form)
                .onSuccess { response ->
                    current?.let { authData ->
                        val updated = authData.copy(
                            user = response.data
                        )
                        sessionStorage.setAuthData(authData = updated.toAuthDataDto())
                        _uiState.update { it.copy(
                            isLoading = false
                        ) }
                        SnackbarController.sendEvent(
                            event = SnackbarEvent(
                                message = response.message
                            )
                        )
                    }
                }.onFailure { dataErrorRemote ->
                    _uiState.update { it.copy(
                        isLoading = false
                    ) }
                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            error = dataErrorRemote
                        )
                    )
                }
        }
    }

}