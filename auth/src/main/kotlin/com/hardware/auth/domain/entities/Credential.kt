package com.hardware.auth.domain.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("credentials")
data class Credential(
    @Id
    val id: String?,
    val email: String,
    var password: String
)
