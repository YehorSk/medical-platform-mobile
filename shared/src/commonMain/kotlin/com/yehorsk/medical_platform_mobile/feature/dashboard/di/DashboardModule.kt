package com.yehorsk.medical_platform_mobile.feature.dashboard.di

import com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.viewmodel.DashboardViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dashboardModule = module {
    viewModelOf(::DashboardViewModel)
}