package com.hardware.auth.persistence
import com.hardware.auth.domain.entities.Credential
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface CredentialsRepository : MongoRepository<Credential, String> {
    @Query("{ 'email': ?0 }")
    fun findByEmail(email: String): Credential?

}