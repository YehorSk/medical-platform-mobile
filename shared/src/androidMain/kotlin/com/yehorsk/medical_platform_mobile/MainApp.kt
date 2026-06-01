package com.yehorsk.medical_platform_mobile

import android.app.Application
import com.yehorsk.medical_platform_mobile.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MainApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MainApp)
        }
    }

}