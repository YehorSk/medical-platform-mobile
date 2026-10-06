package com.yehorsk.medical_platform_mobile.feature.chat.di

import com.yehorsk.medical_platform_mobile.feature.chat.data.ConversationServiceImpl
import com.yehorsk.medical_platform_mobile.feature.chat.data.WebSocketChatConnectionClient
import com.yehorsk.medical_platform_mobile.feature.chat.domain.service.ChatConnectionClient
import com.yehorsk.medical_platform_mobile.feature.chat.domain.service.ConversationService
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.viewmodel.ChatDetailViewModel
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.viewmodel.ChatListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val chatModule = module {
    viewModelOf(::ChatListViewModel)
    viewModelOf(::ChatDetailViewModel)
    singleOf(::ConversationServiceImpl) bind ConversationService::class
    singleOf(::WebSocketChatConnectionClient) bind ChatConnectionClient::class
}