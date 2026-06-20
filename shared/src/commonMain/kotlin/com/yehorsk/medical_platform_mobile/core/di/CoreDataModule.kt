package com.yehorsk.medical_platform_mobile.core.di

import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformCoreDataModule: Module

val coreDataModule = module {
    includes(platformCoreDataModule)
}