package com.yehorsk.medical_platform_mobile.feature.medical.di

import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_card.viewmodel.MedicalCardViewModel
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.viewmodel.CreateMedicalRecordViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val medicalRecordModule = module {
    viewModelOf(::CreateMedicalRecordViewModel)
    viewModelOf(::MedicalCardViewModel)
}