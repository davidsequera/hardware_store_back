package com.hardware.auth.domain.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("credentials")
data class Credentials(
    @Id
    val username: String,
    val password: String
)
