package com.yehorsk.medical_platform_mobile.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.yehorsk.medical_platform_mobile.core.util.appDataDirectory
import java.io.File

fun createDataStore(): DataStore<Preferences> = createDataStore {
    val directory = appDataDirectory

    if(!directory.exists()) {
        directory.mkdirs()
    }

    File(directory, DATA_STORE_FILE_NAME).absolutePath
}