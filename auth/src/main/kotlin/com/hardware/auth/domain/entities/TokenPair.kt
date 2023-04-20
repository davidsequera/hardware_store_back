package com.hardware.auth.domain.entities

/**
 * Data class representing a pair of access and refresh tokens.
 * @property accessToken the access token as a [Token] object.
 * @property refreshToken the refresh token as a [Token] object.
 */
data class TokenPair (
    val accessToken: Token,
    val refreshToken: Token
)
