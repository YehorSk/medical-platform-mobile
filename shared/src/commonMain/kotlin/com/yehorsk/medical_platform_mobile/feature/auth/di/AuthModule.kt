package com.yehorsk.medical_platform_mobile.feature.auth.di

import com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot.viewmodel.ForgotScreenViewModel
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel.LoginScreenViewModel
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel.RegisterScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {
    viewModelOf(::LoginScreenViewModel)
    viewModelOf(::RegisterScreenViewModel)
    viewModelOf(::ForgotScreenViewModel)
}