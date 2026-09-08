package com.yehorsk.medical_platform_mobile.core.data.datastore

import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

actual object PinHasher {

    private const val ALGORITHM = "PBKDF2WithHmacSHA256"
    private const val ITERATIONS = 120_000
    private const val KEY_LENGTH = 256
    private const val SALT_SIZE = 16

    actual fun generateSalt(): ByteArray {
        return ByteArray(SALT_SIZE).also {
            SecureRandom().nextBytes(it)
        }
    }

    actual fun hash(
        pin: String,
        salt: ByteArray
    ): ByteArray {
        val spec = PBEKeySpec(
            pin.toCharArray(),
            salt,
            ITERATIONS,
            KEY_LENGTH
        )

        return try {
            SecretKeyFactory
                .getInstance(ALGORITHM)
                .generateSecret(spec)
                .encoded
        } finally {
            spec.clearPassword()
        }
    }

    actual fun equals(
        first: ByteArray,
        second: ByteArray
    ): Boolean {
        return MessageDigest.isEqual(first, second)
    }
}