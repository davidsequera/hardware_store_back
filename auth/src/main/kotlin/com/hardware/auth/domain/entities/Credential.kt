package com.hardware.auth.domain.entities

import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Data class representing a user credential with its ID, email, and password.
 * @property id the unique ID of the user as a [String].
 * @property email the email address of the user as a [String].
 * @property password the password of the user as a [String]. This property is mutable for authentication purposes.
 */
@NoArgsConstructor
@Document("credentials")
data class Credential(
    @Id
    val id: String?,
    val email: String,
    var password: String
)
