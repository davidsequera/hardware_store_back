package com.hardware.auth.domain.entities

import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
@NoArgsConstructor
@Document("credentials")
data class Credential(
    @Id
    val id: String?,
    val email: String,
    var password: String
)
