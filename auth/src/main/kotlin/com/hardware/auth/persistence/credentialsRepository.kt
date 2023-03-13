package com.hardware.auth.persistence
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import com.hardware.auth.domain.entities.Credentials
interface credentialsRepository : ReactiveMongoRepository<Credentials, String> {
    fun findByUsername(username: String): Credentials

}