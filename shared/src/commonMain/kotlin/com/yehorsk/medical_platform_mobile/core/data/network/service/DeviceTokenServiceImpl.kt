package com.yehorsk.medical_platform_mobile.core.data.network.service

import com.yehorsk.medical_platform_mobile.core.data.network.delete
import com.yehorsk.medical_platform_mobile.core.data.network.dto.request.DevicePlatformDto
import com.yehorsk.medical_platform_mobile.core.data.network.dto.request.RegisterDeviceRequestDto
import com.yehorsk.medical_platform_mobile.core.data.network.post
import com.yehorsk.medical_platform_mobile.core.domain.service.DeviceTokenService
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.core.util.EmptyResult
import io.ktor.client.HttpClient
import io.ktor.http.encodeURLPathPart

class DeviceTokenServiceImpl(
    private val httpClient: HttpClient
): DeviceTokenService {

    // The response body (DeviceTokenDto) isn't needed on the client, so it's discarded as Unit.
    override suspend fun register(token: String): EmptyResult<DataError.Remote> {
        return httpClient.post<RegisterDeviceRequestDto, Unit>(
            route = "/notification/register",
            body = RegisterDeviceRequestDto(
                token = token,
                platform = DevicePlatformDto.ANDROID
            )
        )
    }

    // The backend returns 200 with an empty body; Ktor maps that to Unit.
    override suspend fun unregister(token: String): EmptyResult<DataError.Remote> {
        return httpClient.delete<Unit>(
            route = "/notification/${token.encodeURLPathPart()}"
        )
    }

}
