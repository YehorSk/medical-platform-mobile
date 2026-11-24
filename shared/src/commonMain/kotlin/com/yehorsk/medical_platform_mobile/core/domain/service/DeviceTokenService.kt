package com.yehorsk.medical_platform_mobile.core.domain.service

import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.EmptyResult

// Registers this device's push token for the current user on the backend.
interface DeviceTokenService {

    suspend fun register(token: String): EmptyResult<DataError.Remote>

    suspend fun unregister(token: String): EmptyResult<DataError.Remote>

}
