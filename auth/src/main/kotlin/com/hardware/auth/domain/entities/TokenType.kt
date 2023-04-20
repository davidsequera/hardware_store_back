package com.hardware.auth.domain.entities

/**
 * Enum class representing the type of a token.
 * @property ACCESS represents an access token.
 * @property REFRESH represents a refresh token.
 */
enum class TokenType {
    ACCESS,
    REFRESH
}
