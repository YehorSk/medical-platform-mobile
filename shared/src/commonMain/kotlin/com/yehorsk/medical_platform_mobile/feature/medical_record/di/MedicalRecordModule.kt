package com.yehorsk.medical_platform_mobile.feature.medical_record.di

import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record.viewmodel.CreateMedicalRecordViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val medicalRecordModule = module {
    viewModelOf(::CreateMedicalRecordViewModel)
}