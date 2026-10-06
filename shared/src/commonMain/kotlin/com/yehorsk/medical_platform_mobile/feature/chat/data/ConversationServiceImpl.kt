package com.yehorsk.medical_platform_mobile.feature.chat.data

import com.yehorsk.medical_platform_mobile.core.data.network.dto.response.ApiResponseWithData
import com.yehorsk.medical_platform_mobile.core.data.network.get
import com.yehorsk.medical_platform_mobile.core.data.network.post
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.Result
import com.yehorsk.medical_platform_mobile.core.util.map
import com.yehorsk.medical_platform_mobile.feature.auth.data.dto.AuthDataDto
import com.yehorsk.medical_platform_mobile.feature.auth.data.mappers.toAuthData
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel.LoginForm
import com.yehorsk.medical_platform_mobile.feature.chat.data.dto.ConversationDto
import com.yehorsk.medical_platform_mobile.feature.chat.data.dto.MessageDto
import com.yehorsk.medical_platform_mobile.feature.chat.data.mappers.toDomain
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Conversation
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Message
import com.yehorsk.medical_platform_mobile.feature.chat.domain.service.ConversationService
import io.ktor.client.HttpClient

class ConversationServiceImpl(
    private val httpClient: HttpClient
): ConversationService {

    override suspend fun getAllConversations(): Result<ApiResponseWithData<List<Conversation>>, DataError.Remote> {
        return httpClient.get<ApiResponseWithData<List<ConversationDto>>>(
            route = "/conversations"
        ).map { response ->
            ApiResponseWithData(
                data = response.data.map { it.toDomain() },
                message = response.message
            )
        }
    }

    override suspend fun getConversationMessages(
        conversationId: String,
        limit: Int
    ): Result<ApiResponseWithData<List<Message>>, DataError.Remote> {
        return httpClient.get<ApiResponseWithData<List<MessageDto>>>(
            route = "/conversations/$conversationId/messages",
            queryParams = mapOf("pageSize" to limit)
        ).map { response ->
            ApiResponseWithData(
                data = response.data.map { it.toDomain() },
                message = response.message
            )
        }
    }

    override suspend fun getConversationById(id: String): Result<ApiResponseWithData<Conversation>, DataError.Remote> {
        return httpClient.get<ApiResponseWithData<ConversationDto>>(
            route = "/conversations/$id"
        ).map { response ->
            ApiResponseWithData(
                data = response.data.toDomain(),
                message = response.message
            )
        }
    }

}