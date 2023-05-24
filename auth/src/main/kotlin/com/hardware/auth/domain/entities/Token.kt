package com.hardware.auth.domain.entities

/**
 * Data class representing a token with its type and value.
 * @property type the type of the token as a [TokenType] enum value.
 * @property value the value of the token as a nullable [String].
 */
data class Token(
    val type: TokenType,
    var value: String?,
)
