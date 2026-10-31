package com.yehorsk.medical_platform_mobile.core.data.network

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull
import kotlin.io.encoding.Base64
import kotlin.time.Instant

object JwtUtils {

    private val base64Url = Base64.UrlSafe.withPadding(Base64.PaddingOption.ABSENT_OPTIONAL)

    // Reads the `exp` claim without verifying the signature (the server does that)
    fun expiresAt(token: String): Instant? {
        return try {
            val payload = token.split('.').getOrNull(1) ?: return null
            val claims = Json.parseToJsonElement(base64Url.decode(payload).decodeToString()).jsonObject
            claims["exp"]?.jsonPrimitive?.longOrNull?.let { Instant.fromEpochSeconds(it) }
        } catch (e: Exception) {
            null
        }
    }
}
