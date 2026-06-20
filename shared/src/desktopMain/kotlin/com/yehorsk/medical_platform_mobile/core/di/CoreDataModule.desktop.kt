package com.yehorsk.medical_platform_mobile.core.di

import com.yehorsk.medical_platform_mobile.core.data.datastore.createDataStore
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

actual val platformCoreDataModule = module {
    single { createDataStore() }
    single<HttpClientEngine> { OkHttp.create() }
}