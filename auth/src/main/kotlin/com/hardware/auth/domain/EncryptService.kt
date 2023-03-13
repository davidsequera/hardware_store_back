package com.hardware.auth.domain

import org.springframework.security.crypto.bcrypt.BCrypt

class EncryptService {
    val COST = 12
    fun hashPassword(password: String?): String {
        return BCrypt.hashpw(password, BCrypt.gensalt(COST))
    }

    fun verifyPassword(password: String?, hash: String?): Boolean {
        return BCrypt.checkpw(password, hash)
    }
}
