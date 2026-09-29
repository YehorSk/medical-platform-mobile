package com.yehorsk.medical_platform_mobile

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.yehorsk.medical_platform_mobile.di.initKoin
import io.github.vinceglb.filekit.FileKit

fun main() {
    initKoin()
    FileKit.init(appId = "com.yehorsk.medical_platform_mobile")
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Medicalplatformmobile",
        ) {
            App()
        }
    }
}