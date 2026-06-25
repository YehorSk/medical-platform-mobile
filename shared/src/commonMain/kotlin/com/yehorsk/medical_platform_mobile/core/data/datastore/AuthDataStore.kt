package com.yehorsk.medical_platform_mobile.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

class AuthDataStore(
    private val dataStore: DataStore<Preferences>
) {
}