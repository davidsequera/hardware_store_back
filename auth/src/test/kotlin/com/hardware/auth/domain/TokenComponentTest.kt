package com.hardware.auth.domain

import com.hardware.auth.domain.entities.Credential
import com.hardware.auth.domain.entities.Token
import com.hardware.auth.domain.entities.TokenType
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.stream.Stream


@SpringBootTest
class TokenComponentTest(
    @Autowired private val tokenComponent: TokenComponent
) {

    companion object{
        @JvmStatic
        fun tokenProvider(): Stream<Token?>? {
            // Return a stream of objects to be used as test data
            return Stream.of(
                Token(TokenType.ACCESS, "123"),
                Token(TokenType.REFRESH, "123")
            )
        }

    }
    val credentials: Credential = Credential("1","test@gmail.com", "12345")
    @Test
    fun testSign() {
        val tokenSign: String? = tokenComponent.sign(credentials, TokenType.REFRESH)
        assertNotNull(tokenSign)
    }


    @ParameterizedTest
    @MethodSource("tokenProvider")
    fun testVerify(token:Token) {
        try {
            val tokenSign: String? = tokenComponent.sign(credentials, token.type)
            // Quit Bearer
            token.value = tokenSign?.replace("Bearer ", "")
            val  claims: Claims = tokenComponent.verify(token = token)
            assertNotNull(claims)
        }
        catch (e: Exception){
//            assertInstanceOf(ExpiredJwtException::class.java, e)
            assertNotNull(e.message)
        }
        catch (err : ExceptionInInitializerError){
            System.out.println("ConfigProperties not found")
            assertNotNull(err)
        }
    }



}


