package com.hardware.auth.domain

import com.hardware.auth.domain.entities.Credential
import com.hardware.auth.domain.entities.Token
import com.hardware.auth.domain.entities.TokenType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.hardware.auth.persistence.CredentialsRepository

@Service
class AuthService {

    @Autowired
    lateinit var credentialsRepository: CredentialsRepository


    @Autowired
    lateinit var encryptComponent: EncryptComponent

    @Autowired
    lateinit var tokenComponent: TokenComponent


    fun authenticate(c: Credential): Pair<Token, Token> {
            val credential = credentialsRepository.findByEmail(c.email)
            if (credential != null) {
                if (!encryptComponent.verifyPassword(c.password, credential.password)) {
                    throw Exception("Invalid credentials")
                }
            }
            val refresh = Token(value = credential?.let { tokenComponent.sign(it, TokenType.REFRESH) }, type = TokenType.REFRESH)
            val access = this.refresh(refresh)
            return  Pair(refresh, access)
    }


    fun create(c: Credential): Token? {
        c.password = encryptComponent.hashPassword(c.password)
        return credentialsRepository.save(c).let { this.authenticate(it).second }
    }
    fun refresh(token: Token): Token {

        if (token.type != TokenType.REFRESH || tokenComponent.verify(token) || tokenComponent.expire(token)) {
            throw Exception("Invalid token")
        }
        val claims = tokenComponent.getClaims(token)
        val email = claims["email"] as String
        val credential = credentialsRepository.findByEmail(email)
        return Token(value = credential?.let { tokenComponent.sign(it, TokenType.ACCESS) }, type = TokenType.ACCESS)
    }



}