package com.yehorsk.medical_platform_mobile

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.yehorsk.medical_platform_mobile.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Medicalplatformmobile",
        ) {
            App()
        }
    }
}