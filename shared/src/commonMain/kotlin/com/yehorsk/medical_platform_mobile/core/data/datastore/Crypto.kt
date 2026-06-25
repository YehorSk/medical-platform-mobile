package com.yehorsk.medical_platform_mobile.core.data.datastore

expect object Crypto {
    fun encrypt(bytes: ByteArray): ByteArray
    fun decrypt(bytes: ByteArray): ByteArray
}