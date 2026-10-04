package com.yehorsk.medical_platform_mobile.feature.chat.domain.service

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Conversation
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Message

interface ConversationService {

    suspend fun getAllConversations(): Result<ApiResponseWithData<List<Conversation>>, DataError.Remote>

    suspend fun getConversationMessages(): Result<ApiResponseWithData<List<Message>>, DataError.Remote>

    suspend fun getConversationById(id: String): Result<ApiResponseWithData<Conversation>, DataError.Remote>

}