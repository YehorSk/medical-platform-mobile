package com.yehorsk.medical_platform_mobile.feature.chat.di

import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.viewmodel.ChatListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val chatModule = module {
    viewModelOf(::ChatListViewModel)
}