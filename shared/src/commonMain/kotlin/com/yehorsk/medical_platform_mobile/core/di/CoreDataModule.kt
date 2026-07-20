package com.yehorsk.medical_platform_mobile.core.di

import com.yehorsk.medical_platform_mobile.core.data.datastore.SessionDataStore
import com.yehorsk.medical_platform_mobile.core.data.datastore.SettingsDataStore
import com.yehorsk.medical_platform_mobile.core.data.logger.KermitLogger
import com.yehorsk.medical_platform_mobile.core.data.network.DoctorServiceImpl
import com.yehorsk.medical_platform_mobile.core.data.network.SpecializationServiceImpl
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.domain.repository.SessionStorage
import com.yehorsk.medical_platform_mobile.core.domain.repository.SettingsStorage
import com.yehorsk.medical_platform_mobile.core.domain.service.DoctorService
import com.yehorsk.medical_platform_mobile.core.domain.service.SpecializationService
import com.yehorsk.medical_platform_mobile.core.util.AuthEventManager
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformCoreDataModule: Module

val coreDataModule = module {
    includes(platformCoreDataModule)
    singleOf(::SessionDataStore) bind SessionStorage::class
    singleOf(::SettingsDataStore) bind SettingsStorage::class
    singleOf(::SpecializationServiceImpl) bind SpecializationService::class
    singleOf(::DoctorServiceImpl) bind DoctorService::class
    single<MainLogger> { KermitLogger }
    single { AuthEventManager }
}