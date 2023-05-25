package com.hardware.auth.presentation

import com.hardware.auth.domain.AuthService
import com.hardware.auth.domain.entities.Credential
import com.hardware.auth.domain.entities.Token
import com.hardware.auth.domain.entities.TokenPair
import com.hardware.auth.domain.entities.TokenType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

/**
 * This controller handles the authentication requests.
 *
 * @param authService The service that handles the authentication logic.
 */
@Controller
class AuthController(@Autowired val authService: AuthService) {

    /**
     * Authenticates the user with the given credentials.
     *
     * @param credential The user's credentials.
     * @return A pair of tokens (access token and refresh token) if the authentication is successful.
     */
    @MutationMapping
    fun authenticate(@Argument credential: Credential): TokenPair {
        val pair: Pair<Token, Token> = authService.authenticate(credential)
        return TokenPair(pair.first, pair.second)
    }

    /**
     * Creates a new account for the user with the given credentials.
     *
     * @param credential The user's credentials.
     * @return A pair of tokens (access token and refresh token) if the account creation is successful.
     */
    @MutationMapping
    fun createAccount(@Argument credential: Credential): TokenPair {
        val pair: Pair<Token, Token> = authService.create(credential)
        return TokenPair(pair.first, pair.second)
    }

    /**
     * Refreshes the access token for the user with the given refresh token.
     *
     * @param tokenString The refresh token.
     * @return The new access token if the refresh is successful.
     */
    @QueryMapping
    fun accessToken(@Argument tokenString: String): Token {
        return authService.refresh(Token(value = tokenString, type = TokenType.REFRESH))
    }

}
