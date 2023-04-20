package com.hardware.auth.persistence

import com.hardware.auth.domain.entities.Credential
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

/**
 * Repository for CRUD operations on credentials stored in a MongoDB database.
 */
interface CredentialsRepository : MongoRepository<Credential, String> {

    /**
     * Finds the credential with the given email address.
     *
     * @param email The email address to search for.
     * @return The credential with the given email address, or null if not found.
     */
    @Query("{ 'email': ?0 }")
    fun findByEmail(email: String): Credential?

}
