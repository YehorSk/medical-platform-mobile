package com.yehorsk.medical_platform_mobile.core.di

import com.yehorsk.medical_platform_mobile.core.data.datastore.createDataStore
import com.yehorsk.medical_platform_mobile.core.data.lifecycle.AppLifecycleObserver
import com.yehorsk.medical_platform_mobile.core.data.network.ConnectionErrorHandler
import com.yehorsk.medical_platform_mobile.core.data.network.ConnectivityObserver
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformCoreDataModule = module {
    single { createDataStore() }
    singleOf(::AppLifecycleObserver)
    single<HttpClientEngine> { OkHttp.create() }
    singleOf(::ConnectivityObserver)
    singleOf(::ConnectionErrorHandler)
}