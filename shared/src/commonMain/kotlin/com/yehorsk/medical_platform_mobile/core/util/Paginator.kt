package com.yehorsk.medical_platform_mobile.core.util

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.PagedResponseDto
import com.yehorsk.medical_platform_mobile.core.domain.model.Doctor
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive

class Paginator<Key : Any, Item>(
    private val initialKey: Key,
    private val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: suspend (nextKey: Key) -> Result<PagedResponseDto<Item>, DataError>,
    private val getNextKey: (current: Key, response: PagedResponseDto<Item>) -> Key?,
    private val onError: suspend (DataError?) -> Unit,
    private val onSuccess: suspend (items: PagedResponseDto<Item>, newKey: Key?) -> Unit
) {
    private var currentKey: Key? = initialKey
    private var isMakingRequest = false

    suspend fun loadNextItems() {
        val key = currentKey ?: return
        if (isMakingRequest) return

        isMakingRequest = true
        onLoadUpdated(true)

        try {
            onRequest(key)
                .onSuccess { response ->
                    val newKey = getNextKey(key, response)
                    onSuccess(response, newKey)
                    currentKey = newKey
                }
                .onFailure { error ->
                    onError(error)
                }
        } catch (e: Exception) {
            currentCoroutineContext().ensureActive()
            onError(DataError.Remote.Status.UNKNOWN)
        } finally {
            onLoadUpdated(false)
            isMakingRequest = false
        }
    }

    fun reset() {
        currentKey = initialKey
    }
}