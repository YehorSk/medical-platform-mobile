package com.yehorsk.medical_platform_mobile.feature.connections.di

import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.viewmodel.FindDoctorViewModel
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.main.viewmodel.ConnectionsMainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val connectionsModule = module {
    viewModelOf(::ConnectionsMainViewModel)
    viewModelOf(::FindDoctorViewModel)
}