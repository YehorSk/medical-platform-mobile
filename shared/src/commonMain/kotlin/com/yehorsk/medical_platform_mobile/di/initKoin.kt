package com.yehorsk.medical_platform_mobile.di

import com.yehorsk.medical_platform_mobile.feature.auth.di.authModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null){
    startKoin {
        config?.invoke(this)
        modules(
            authModule
        )
    }
}