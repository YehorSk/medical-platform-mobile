package com.yehorsk.medical_platform_mobile.feature.chat.domain.service

import com.yehorsk.medical_platform_mobile.core.data.network.ConnectionState
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.EmptyResult
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ChatConnectionClient {
    val chatMessages: Flow<Message>
    val connectionState: StateFlow<ConnectionState>
    suspend fun sendMessage(conversationId: String, content: String): EmptyResult<DataError.Connection>
}