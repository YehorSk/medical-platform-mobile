package com.yehorsk.medical_platform_mobile.feature.connections.di

import com.yehorsk.medical_platform_mobile.feature.connections.data.network.service.MedicalCardServiceImpl
import com.yehorsk.medical_platform_mobile.feature.connections.data.network.service.PatientHasDoctorServiceImpl
import com.yehorsk.medical_platform_mobile.feature.connections.domain.service.MedicalCardService
import com.yehorsk.medical_platform_mobile.feature.connections.domain.service.PatientHasDoctorService
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.doctor_details.viewmodel.DoctorDetailsViewModel
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.viewmodel.FindDoctorViewModel
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_patient.viewmodel.FindPatientViewModel
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.main.viewmodel.ConnectionsMainViewModel
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.patient_details.viewmodel.PatientDetailsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val connectionsModule = module {
    viewModelOf(::ConnectionsMainViewModel)
    viewModelOf(::FindDoctorViewModel)
    viewModelOf(::FindPatientViewModel)
    viewModelOf(::DoctorDetailsViewModel)
    viewModelOf(::PatientDetailsViewModel)
    singleOf(::PatientHasDoctorServiceImpl) bind PatientHasDoctorService::class
    singleOf(::MedicalCardServiceImpl) bind MedicalCardService::class
}