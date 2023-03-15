package com.hardware.auth.presentation

import com.hardware.auth.domain.AuthService
import com.hardware.auth.domain.entities.Credential
import com.hardware.auth.domain.entities.Token
import com.hardware.auth.domain.entities.TokenType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono


@Controller
class AuthController(@Autowired val authService: AuthService) {

    @MutationMapping
    fun authenticate(@Argument credential: Mono<Credential>): Token {
        return authService.authenticate(credential).second
    }

    @MutationMapping
    fun createAccount(@Argument credential: Credential): Token? {
        return authService.create(credential)
    }
    @QueryMapping
    fun accessToken(@Argument tokenString: String): Token{
        return authService.refresh(Token(value = tokenString, type = TokenType.REFRESH))
    }


}