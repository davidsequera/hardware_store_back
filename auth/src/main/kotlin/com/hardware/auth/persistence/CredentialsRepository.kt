package com.hardware.auth.persistence
import com.hardware.auth.domain.entities.Credential
import org.springframework.data.mongodb.repository.MongoRepository

interface CredentialsRepository : MongoRepository<Credential, String> {
    fun findByEmail(email: String): Credential?

}