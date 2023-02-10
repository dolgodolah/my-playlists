package com.myplaylists.utils

import org.apache.tomcat.util.buf.HexUtils
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object CryptoUtils {

    fun encrypt(value: Long, secretKey: String) = encrypt(value.toString(), secretKey)

    private fun encrypt(value: String, secretKey: String): String {
        val iv = secretKey.substring(0, 16)
        val keyData = secretKey.toByteArray()
        val secureKey = SecretKeySpec(keyData, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secureKey, IvParameterSpec(iv.toByteArray()))
        val encrypted = cipher.doFinal(value.toByteArray(Charsets.UTF_8))
        return HexUtils.toHexString(encrypted)
    }

    fun decrypt(value: String, secretKey: String): String {
        val iv = secretKey.substring(0, 16)
        val keyData = secretKey.toByteArray()
        val secureKey: SecretKey = SecretKeySpec(keyData, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secureKey, IvParameterSpec(iv.toByteArray(charset("UTF-8"))))
        val bytes = HexUtils.fromHexString(value)
        return String(cipher.doFinal(bytes), Charsets.UTF_8)
    }
}