package com.yehorsk.medical_platform_mobile.core.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object AuthEventManager {
    private val _authEvents = MutableSharedFlow<AuthEvent>(extraBufferCapacity = 1)
    val authEvents = _authEvents.asSharedFlow()

    fun triggerLogout() {
        _authEvents.tryEmit(AuthEvent.NavigateToLogin)
    }
}

sealed interface AuthEvent {
    data object NavigateToLogin : AuthEvent
}