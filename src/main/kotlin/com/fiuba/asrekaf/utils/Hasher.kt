package com.fiuba.asrekaf.utils

import java.security.MessageDigest

enum class HashType(val algorithm: String) { SHA_256("SHA-256"), SHA3_512("SHA3-512") }

object Hasher {

    fun hash(nonHashedStr: String, hashType: HashType): String {
        val bytes = nonHashedStr.toByteArray()
        val md = MessageDigest.getInstance(hashType.algorithm)
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }

    fun verify(nonHashedStr: String, hashedStr: String, hashType: HashType): Boolean =
        hash(nonHashedStr, hashType) == hashedStr

}
