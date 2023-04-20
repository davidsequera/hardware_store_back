/**
 * This component provides methods for password encryption and verification.
 */
package com.hardware.auth.domain

import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Component

@Component
class EncryptComponent {
    // The cost factor used in generating the salt for the hash function.
    private val COST = 12

    /**
     * Hashes the given password using the BCrypt hash function with a randomly generated salt.
     *
     * @param password The password to hash.
     * @return The hashed password.
     */
    fun hashPassword(password: String?): String {
        return BCrypt.hashpw(password, BCrypt.gensalt(COST))
    }

    /**
     * Verifies the given password against the given hash.
     *
     * @param password The password to verify.
     * @param hash The hash to verify the password against.
     * @return `true` if the password matches the hash, `false` otherwise.
     */
    fun verifyPassword(password: String?, hash: String?): Boolean {
        return BCrypt.checkpw(password, hash)
    }
}
