package com.yehorsk.medical_platform_mobile.core.data.datastore

expect object PinHasher {

    fun generateSalt(): ByteArray

    fun hash(
        pin: String,
        salt: ByteArray
    ): ByteArray

    fun equals(
        first: ByteArray,
        second: ByteArray
    ): Boolean
}