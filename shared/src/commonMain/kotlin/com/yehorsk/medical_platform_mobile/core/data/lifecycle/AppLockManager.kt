package com.yehorsk.medical_platform_mobile.core.data.lifecycle

import com.yehorsk.medical_platform_mobile.core.domain.repository.SessionStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AppLockManager(
    appLifecycleObserver: AppLifecycleObserver,
    sessionStorage: SessionStorage,
    applicationScope: CoroutineScope
) {

    private val isUnlocked = MutableStateFlow(false)

    private val _lockEvents = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val lockEvents: Flow<Unit> = _lockEvents.asSharedFlow()

    private var backgroundedAt: Long? = null

    init {
        applicationScope.launch {
            appLifecycleObserver.isInForeground
                .distinctUntilChanged()
                .collect { isInForeground ->
                    if (!isInForeground) {
                        backgroundedAt = elapsedRealtimeMillis()
                        return@collect
                    }
                    val since = backgroundedAt
                    backgroundedAt = null
                    if (
                        isUnlocked.value &&
                        since != null &&
                        elapsedRealtimeMillis() - since >= RELOCK_AFTER_MS
                    ) {
                        isUnlocked.value = false
                        _lockEvents.emit(Unit)
                    }
                }
        }

        applicationScope.launch {
            sessionStorage.observeAuthData()
                .map { it != null }
                .distinctUntilChanged()
                .collect { hasSession ->
                    if (!hasSession) {
                        isUnlocked.value = false
                    }
                }
        }
    }

    fun markUnlocked() {
        isUnlocked.value = true
    }

    private companion object {
        const val RELOCK_AFTER_MS = 60_000L
    }
}
