package com.yehorsk.medical_platform_mobile.feature.settings.di

import com.yehorsk.medical_platform_mobile.feature.settings.data.SettingsServiceImpl
import com.yehorsk.medical_platform_mobile.feature.settings.domain.SettingsService
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.update_password.viewmodel.UpdatePasswordViewModel
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.viewmodel.SettingsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val settingsModule = module {
    viewModelOf(::SettingsViewModel)
    viewModelOf(::UpdatePasswordViewModel)
    singleOf(::SettingsServiceImpl) bind SettingsService::class
}