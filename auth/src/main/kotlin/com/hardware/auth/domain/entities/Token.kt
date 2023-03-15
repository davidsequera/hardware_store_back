package com.hardware.auth.domain.entities

data class Token(
    val type: TokenType,
    val value: String?,
)
