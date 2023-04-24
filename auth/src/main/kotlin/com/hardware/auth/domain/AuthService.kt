/**
 * This service handles authentication-related operations.
 */
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

    /**
     * Authenticates the user with the given credentials and returns a pair of access and refresh tokens.
     *
     * @param c The user's credentials to authenticate.
     * @return A pair of access and refresh tokens.
     * @throws GraphQLAuthException If the user is not found or the credentials are incorrect.
     */
    fun authenticate(c: Credential): Pair<Token, Token> {
        val storedCredential = credentialsRepository.findByEmail(c.email)
            ?: throw GraphQLAuthException("User not found")

        if (!encryptComponent.verifyPassword(c.password, storedCredential.password)) {
            throw GraphQLAuthException("Incorrect email or password.")
        }

        val accessToken =
            Token(value = tokenComponent.sign(storedCredential, TokenType.ACCESS), type = TokenType.ACCESS)
        val refreshToken =
            Token(value = tokenComponent.sign(storedCredential, TokenType.REFRESH), type = TokenType.REFRESH)

        return Pair(accessToken, refreshToken)
    }

    /**
     * Creates a new user with the given credentials and returns a pair of access and refresh tokens.
     *
     * @param c The user's credentials to create.
     * @return A pair of access and refresh tokens.
     * @throws GraphQLAuthException If the user already exists.
     */
    fun create(c: Credential): Pair<Token, Token> {
        val credential = c.copy()
        credential.password = encryptComponent.hashPassword(c.password)

        try {
            credentialsRepository.save(credential)
        } catch (e: Exception) {
            throw GraphQLAuthException("User already exists")
        }

        return this.authenticate(c)
    }

    /**
     * Refreshes an access token with a new one.
     *
     * @param token The refresh token to use for authentication.
     * @return The new access token.
     * @throws GraphQLAuthException If the token is invalid or has expired.
     */
    fun refresh(token: Token): Token {
        if (token.type != TokenType.REFRESH){
            throw GraphQLAuthException("Invalid token must enter a refresh token")
        }
        if(!tokenComponent.verify(token)){
            throw GraphQLAuthException("Invalid token")
        }
        if(!tokenComponent.current(token)) {
            throw GraphQLAuthException("Expired token")
        }

        val claims = tokenComponent.getClaims(token)
        val email = claims["email"] as String
        val credential = credentialsRepository.findByEmail(email)

        return Token(value = credential?.let { tokenComponent.sign(it, TokenType.ACCESS) }, type = TokenType.ACCESS)
    }
}
