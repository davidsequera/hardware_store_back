package com.hardware.auth.domain

import com.hardware.auth.domain.entities.Credential
import com.hardware.auth.domain.entities.Token
import com.hardware.auth.domain.entities.TokenType
import com.hardware.auth.domain.exceptions.GraphQLAuthException
import com.hardware.auth.persistence.CredentialsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthService {

    @Autowired
    lateinit var credentialsRepository: CredentialsRepository


    @Autowired
    lateinit var encryptComponent: EncryptComponent

    @Autowired
    lateinit var tokenComponent: TokenComponent


    fun authenticate(c: Credential): Pair<Token, Token> {
        val storedCredential = credentialsRepository.findByEmail(c.email) ?: throw GraphQLAuthException(
            "Usuario no encontrado"
        )

        if (!encryptComponent.verifyPassword(c.password, storedCredential.password)) {
            throw GraphQLAuthException("Email o Contrasena Invalido")
        }

        val accessToken = Token(value = tokenComponent.sign(storedCredential, TokenType.ACCESS), type = TokenType.ACCESS)
        val refreshToken = Token(value = tokenComponent.sign(storedCredential, TokenType.REFRESH), type = TokenType.REFRESH)

        return Pair(accessToken, refreshToken)
    }


    fun create(c: Credential): Token? {
        val credential = c.copy()
        credential.password = encryptComponent.hashPassword(c.password)
        credentialsRepository.save(credential)
        return this.authenticate(c).second
    }
    fun refresh(token: Token): Token {

        if (token.type != TokenType.REFRESH || !tokenComponent.verify(token) || !tokenComponent.current(token)) {
            throw GraphQLAuthException("Invalid token")
        }
        val claims = tokenComponent.getClaims(token)
        val email = claims["email"] as String
        val credential = credentialsRepository.findByEmail(email)
        return Token(value = credential?.let { tokenComponent.sign(it, TokenType.ACCESS) }, type = TokenType.ACCESS)
    }
}