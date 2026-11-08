package com.yehorsk.medical_platform_mobile.feature.auth.presentation.local_auth.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.domain.repository.PinStorage
import com.yehorsk.medical_platform_mobile.core.domain.repository.SessionStorage
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.auth.domain.AuthService
import com.yehorsk.medical_platform_mobile.navigation.Screen
import com.yehorsk.medical_platform_mobile.navigation.navTypes.serializableType
import com.yehorsk.medical_platform_mobile.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.error_pin_incorrect
import medicalplatformmobile.shared.generated.resources.error_pin_invalid
import medicalplatformmobile.shared.generated.resources.error_pin_mismatch
import medicalplatformmobile.shared.generated.resources.error_pin_required
import medicalplatformmobile.shared.generated.resources.error_pin_weak
import kotlin.reflect.typeOf

class LocalAuthScreenViewModel(
    private val pinStorage: PinStorage,
    private val mainLogger: MainLogger,
    private val authService: AuthService,
    private val sessionStorage: SessionStorage,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val eventChannel = Channel<LocalAuthEvent>()
    val events = eventChannel.receiveAsFlow()

    // Only touched on the main thread (onAction + viewModelScope), so a plain var is enough
    private var isSubmitting = false

    // The user comes from the route, so it is known before any PIN check runs.
    private val route = savedStateHandle.toRoute<Screen.LocalAuth>(
        typeMap = mapOf(typeOf<UserRole>() to serializableType<UserRole>())
    )

    private val _uiState = MutableStateFlow(
        LocalAuthState(
            userId = route.userId,
            userRole = route.userRole
        )
    )
    val uiState: StateFlow<LocalAuthState> = _uiState.asStateFlow()

    init {
        // Runs once per ViewModel: re-running on resubscribe would reset an in-progress
        // CREATE -> CONFIRM flow.
        checkPinExists()
    }

    fun onAction(action: LocalAuthAction){
        when(action){
            is LocalAuthAction.OnPinChanged -> onPinChanged(action.value)
            is LocalAuthAction.OnConfirmPinChanged -> onConfirmPinChanged(action.value)
            LocalAuthAction.OnCheckPinClicked -> onCheckClicked()
        }
    }

    private fun onFormValidate(){
        _uiState.update {
            it.copy(
                isFormValid = when(it.localAuthType){
                    LocalAuthType.CREATE_PIN -> isPinValid(it.pinCode)
                    LocalAuthType.CONFIRM_PIN ->
                        isPinValid(it.pinCodeConfirm) &&
                                it.pinCode == it.pinCodeConfirm
                    LocalAuthType.ENTER_PIN -> isPinValid(it.pinCode)
                    null -> false
                }
            )
        }
    }

    private fun onConfirmPinChanged(value: String) {
        _uiState.update {
            it.copy(
                pinCodeConfirm = value,
                error = null
            )
        }
        onFormValidate()
    }

    private fun onPinChanged(value: String) {
        _uiState.update {
            it.copy(
                pinCode = value,
                error = null
            )
        }
        onFormValidate()
    }

    // Runs inside the submit coroutine, so the double-tap guard covers it too
    private suspend fun auth() {
        _uiState.update { it.copy(
            isLoading = true,
        ) }
        authService
            .me()
            .onSuccess {
                _uiState.update { it.copy(
                    isLoading = false
                ) }
                eventChannel.send(
                    LocalAuthEvent.Success
                )
            }
            .onFailure {
                _uiState.update { it.copy(
                    isLoading = false
                ) }
                sessionStorage.clearAuthData()
                eventChannel.send(LocalAuthEvent.TooManyFailedAttempts)
            }
    }

    private fun checkPinExists() {
        val id = route.userId

        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            val hasPin = pinStorage.hasPin(id)

            // A lockout interrupted earlier (e.g. app killed mid-way) must still apply
            if (hasPin && pinStorage.getFailedAttempts() >= MAX_PIN_ATTEMPTS) {
                lockOut()
                return@launch
            }

            _uiState.update {
                it.copy(
                    isLoading = false,
                    localAuthType = if (hasPin) {
                        LocalAuthType.ENTER_PIN
                    } else {
                        LocalAuthType.CREATE_PIN
                    }
                )
            }
        }
    }

    private fun onCheckClicked() {
        // A fast double tap would otherwise run two checks at once: two failed
        // attempts counted for one wrong PIN, or auth() called twice
        if (isSubmitting) return
        isSubmitting = true

        val id = route.userId
        viewModelScope.launch {
            try {
                submit(id)
            } finally {
                isSubmitting = false
            }
        }
    }

    private suspend fun submit(id: String) {
        when(_uiState.value.localAuthType){
            // Still checking whether a PIN exists: nothing to submit yet
            null -> return
            LocalAuthType.CREATE_PIN -> {
                if (!isPinValid(_uiState.value.pinCode)) {
                    _uiState.update {
                        it.copy(
                            error = UiText.Resource(UiRes.string.error_pin_invalid)
                        )
                    }
                    return
                }

                // Only checked when creating: an existing weak PIN must still unlock
                if (isPinWeak(_uiState.value.pinCode)) {
                    _uiState.update {
                        it.copy(
                            error = UiText.Resource(UiRes.string.error_pin_weak)
                        )
                    }
                    return
                }

                _uiState.update {
                    it.copy(
                        localAuthType = LocalAuthType.CONFIRM_PIN,
                        pinCodeConfirm = "",
                        error = null
                    )
                }
                onFormValidate()
            }
            LocalAuthType.CONFIRM_PIN -> {
                if (_uiState.value.pinCode != _uiState.value.pinCodeConfirm) {
                    _uiState.update {
                        it.copy(
                            error = UiText.Resource(UiRes.string.error_pin_mismatch)
                        )
                    }
                    return
                }
                // Never overwrite an existing PIN: CREATE/CONFIRM must only be
                // reachable when no PIN is stored for this account.
                if (pinStorage.hasPin(id)) {
                    mainLogger.error("Refusing to overwrite existing PIN")
                    _uiState.update {
                        it.copy(
                            localAuthType = LocalAuthType.ENTER_PIN,
                            pinCode = "",
                            pinCodeConfirm = "",
                            isFormValid = false,
                            error = null
                        )
                    }
                    return
                }
                pinStorage.setPin(id, _uiState.value.pinCode)
                auth()
            }
            LocalAuthType.ENTER_PIN -> {
                if (!isPinValid(_uiState.value.pinCode)) {
                    _uiState.update {
                        it.copy(
                            error = UiText.Resource(UiRes.string.error_pin_required)
                        )
                    }
                    return
                }

                val isCorrect = pinStorage.verifyPin(
                    id,
                    _uiState.value.pinCode
                )

                if (isCorrect) {
                    pinStorage.resetFailedAttempts()
                    auth()
                } else {
                    val attempts = pinStorage.registerFailedAttempt()
                    if (attempts >= MAX_PIN_ATTEMPTS) {
                        lockOut()
                    } else {
                        _uiState.update {
                            it.copy(
                                error = UiText.Resource(UiRes.string.error_pin_incorrect)
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * Too many wrong PINs: revoke the session on the server (best effort) and wipe
     * the local tokens and PIN, so restarting the app can't grant more attempts.
     */
    private suspend fun lockOut() {
        _uiState.update { it.copy(isLoading = true) }
        authService.logout()
        sessionStorage.clearAuthData()
        pinStorage.clearPin()
        _uiState.update { it.copy(isLoading = false) }
        eventChannel.send(LocalAuthEvent.TooManyFailedAttempts)
    }

    private fun isPinValid(pin: String): Boolean {
        return pin.length in 4..6 &&
                pin.all(Char::isDigit)
    }

    /** All one digit (0000, 111111) or a straight run up/down (1234, 654321). */
    private fun isPinWeak(pin: String): Boolean {
        val steps = pin.zipWithNext { a, b -> b.digitToInt() - a.digitToInt() }
        return steps.all { it == 0 } ||
                steps.all { it == 1 } ||
                steps.all { it == -1 }
    }

    private companion object {
        const val MAX_PIN_ATTEMPTS = 5
    }

}
