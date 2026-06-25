package com.yehorsk.medical_platform_mobile.core.data.datastore

import java.io.File
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

actual object Crypto {
    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"
    private val keyFile = File(System.getProperty("user.home"), ".medical_app/secret.key")

    private fun getKey(): SecretKey {
        if (!keyFile.exists()) {
            keyFile.parentFile.mkdirs()
            val key = KeyGenerator.getInstance(ALGORITHM).apply { init(256) }.generateKey()
            keyFile.writeBytes(key.encoded)
        }
        val keyBytes = keyFile.readBytes()
        return SecretKeySpec(keyBytes, ALGORITHM)
    }

    actual fun encrypt(bytes: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getKey())
        val iv = cipher.iv
        val encrypted = cipher.doFinal(bytes)
        return iv + encrypted
    }

    actual fun decrypt(bytes: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val iv = bytes.copyOfRange(0, 16)
        val data = bytes.copyOfRange(16, bytes.size)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
        return cipher.doFinal(data)
    }
}