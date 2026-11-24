package com.yehorsk.medical_platform_mobile.core.data.push

import com.yehorsk.medical_platform_mobile.core.domain.push.PushTarget
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Holds the target of a tapped notification until the UI can navigate to it
// (i.e. the user is signed in and has passed the PIN screen).
class PendingPushNavigation {

    private val _target = MutableStateFlow<PushTarget?>(null)
    val target: StateFlow<PushTarget?> = _target.asStateFlow()

    fun set(target: PushTarget) {
        _target.value = target
    }

    fun consume() {
        _target.value = null
    }

}
