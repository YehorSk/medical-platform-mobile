package com.yehorsk.medical_platform_mobile.di

import com.yehorsk.medical_platform_mobile.core.di.coreDataModule
import com.yehorsk.medical_platform_mobile.feature.auth.di.authModule
import com.yehorsk.medical_platform_mobile.feature.chat.di.chatModule
import com.yehorsk.medical_platform_mobile.feature.connections.di.connectionsModule
import com.yehorsk.medical_platform_mobile.feature.dashboard.di.dashboardModule
import com.yehorsk.medical_platform_mobile.feature.settings.di.settingsModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null){
    startKoin {
        config?.invoke(this)
        modules(
            authModule,
            settingsModule,
            chatModule,
            dashboardModule,
            coreDataModule,
            connectionsModule
        )
    }
}