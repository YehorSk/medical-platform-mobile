package com.yehorsk.medical_platform_mobile.feature.auth.di

import com.yehorsk.medical_platform_mobile.MainViewModel
import com.yehorsk.medical_platform_mobile.core.data.network.HttpClientFactory
import com.yehorsk.medical_platform_mobile.feature.auth.data.AuthServiceImpl
import com.yehorsk.medical_platform_mobile.feature.auth.domain.AuthService
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password.viewmodel.ForgotPasswordScreenViewModel
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel.LoginScreenViewModel
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel.RegisterScreenViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    viewModelOf(::LoginScreenViewModel)
    viewModelOf(::RegisterScreenViewModel)
    viewModelOf(::ForgotPasswordScreenViewModel)
    viewModelOf(::MainViewModel)
    single {
        HttpClientFactory(get(), get(), get()).create(get())
    }
    singleOf(::AuthServiceImpl) bind AuthService::class
}