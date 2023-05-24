package com.hardware.auth.domain

import com.hardware.auth.domain.entities.Credential
import com.hardware.auth.domain.entities.Token
import com.hardware.auth.domain.entities.TokenType
import com.hardware.auth.domain.exceptions.GraphQLAuthException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.stream.Stream

@SpringBootTest
class AuthServiceTest(
    @Autowired private val authService: AuthService
) {

    companion object{
        val credentials:Credential = Credential("1","test@gmail.com", "12345")
        @JvmStatic
        fun credentialsProvider(): Stream<Credential?>? {
            // Return a stream of objects to be used as test data
            return Stream.of(
                credentials,
                Credential("2","test@gmail.com", "not_password"),
                Credential("3","user@fake.com", "12345"),
            )
        }
        @JvmStatic
        fun tokenProvider(): Stream<Token?>? {
            val tokenComponent = TokenComponent()
            val tokenSign: String? = tokenComponent.sign(credentials, TokenType.REFRESH)

            // Return a stream of objects to be used as test data
            return Stream.of(
                Token(TokenType.REFRESH, tokenSign),
                Token(TokenType.ACCESS, tokenSign),
                Token(TokenType.REFRESH, "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI0MDI2Njc3OC0zMWI0LTRlNGYtOWZmNS04MDdkMmZhZWE1YWMiLCJzdWIiOiI2NDRmMWY3ZjliZmY1NDZlOGQ2NjUxZDEiLCJlbWFpbCI6InVzZXJAZ21haWwuY29tIiwidHlwZSI6IlJFRlJFU0giLCJhdXRob3JpdGllcyI6IlJPTEVfVVNFUiIsImlhdCI6MTY4NDI3MzQxNSwiZXhwIjoxNjg0MjczNDE1fQ.85mUMPdoPA28KMJz7dCFfYpFMWJNtsphCYQMW_dSjXvGCJir_R8ArpnmpqEVlsRjTXGsEM0yF5EZ9mT0oNxilg"),
                Token(TokenType.REFRESH, "123")
            )
        }
    }

    /**
     * Authenticates the user with the given credentials and assertNotNull( a pair of access and refresh tokens.)
     *
     * @param c The user's credentials to authenticate.
     * @throws GraphQLAuthException If the user is not found or the credentials are incorrect.
     */
    @ParameterizedTest
    @MethodSource("credentialsProvider")
    fun testAuthenticate(c: Credential) {
        try {
            val testVar: Pair<Token, Token> = authService.authenticate(c)
            assertNotNull(testVar)
            assertInstanceOf(Token::class.java, testVar.first)
            assertInstanceOf(Token::class.java, testVar.second)
        }
        catch (e: GraphQLAuthException){
            System.out.println(e.message)
//            Equals to a message or another
            assertThat(e.message).isIn("User not found","Incorrect email or password.")
        }
    }

    @Test
    fun testCreate() {
        val testVar: Pair<Token, Token>

        try {
            testVar = authService.create(credentials)
            assertNotNull(testVar)
        }catch (e: GraphQLAuthException){
            assertEquals("User already exists", e.message)
        }
    }

    /**
     * Refreshes an access token with a new one.
     *
     * @param token The refresh token to use for authentication.
     * @throws GraphQLAuthException If the token is invalid or has expired.
     */
    @ParameterizedTest
    @MethodSource("tokenProvider")
    fun testRefresh(token: Token) {
        try {
            val testVar = authService.refresh(token)
            assertInstanceOf(Token::class.java, testVar)
        }catch (e: GraphQLAuthException){
//            Equals to a message or another
            assertThat(e.message).isIn("Invalid token must enter a refresh token","Token has expired","Invalid token")
        }
    }
}
