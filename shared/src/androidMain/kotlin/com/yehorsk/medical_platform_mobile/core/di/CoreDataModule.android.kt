package com.yehorsk.medical_platform_mobile.core.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.yehorsk.medical_platform_mobile.core.data.datastore.createDataStore
import com.yehorsk.medical_platform_mobile.core.data.network.ConnectivityObserver
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformCoreDataModule = module {
    single<HttpClientEngine> { OkHttp.create() }
    single<DataStore<Preferences>> {
        createDataStore(androidContext())
    }
    singleOf(::ConnectivityObserver)
}