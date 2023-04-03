package com.hardware.auth.domain

import com.hardware.auth.configuration.ConfigProperties
import com.hardware.auth.domain.entities.Credential
import com.hardware.auth.domain.entities.Token
import com.hardware.auth.domain.entities.TokenType
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*


@Component
class TokenComponent {

    val ACCESS_EXPIRATION = 600000
    val REFRESH_EXPIRATION = 3600000

    fun sign(c: Credential, type : TokenType): String? {
        val secretKey = when(type){
            TokenType.ACCESS -> ConfigProperties.getProperty("JWT_SECRET")
            TokenType.REFRESH -> ConfigProperties.getProperty("JWT_SECRET_REFRESH")
        }
        val expiration = when(type){
            TokenType.ACCESS -> ACCESS_EXPIRATION
            TokenType.REFRESH -> REFRESH_EXPIRATION
        }
        val token = Jwts
            .builder()
            .setId(UUID.randomUUID().toString())
            .setSubject(c.id)
            .claim("email", c.email)
            .claim("type", type.toString())
            .claim("authorities", "ROLE_USER")
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(
                Keys.hmacShaKeyFor(secretKey.toByteArray()),
                SignatureAlgorithm.HS512
            ).compact()
        return "Bearer $token"
    }

    fun verify(token: Token): Boolean {
        var verified: Boolean
        val secretKey = when(token.type){
            TokenType.ACCESS -> ConfigProperties.getProperty("JWT_SECRET")
            TokenType.REFRESH -> ConfigProperties.getProperty("JWT_SECRET_REFRESH")
        }
        try {
            //Claims
            Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.toByteArray()))
                .build()
                .parseClaimsJws(token.value)
                .body
            verified = true
        } catch (e: Exception) {
            verified = false
        }
        return verified
    }


    fun current(token: Token): Boolean {
        val secretKey = when(token.type){
            TokenType.ACCESS -> ConfigProperties.getProperty("JWT_SECRET")
            TokenType.REFRESH -> ConfigProperties.getProperty("JWT_SECRET_REFRESH")
        }
        try {
        val claims = Jwts
            .parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(secretKey.toByteArray()))
            .build()
            .parseClaimsJws(token.value)
            .body

            return !claims.expiration.before(Date())
        }catch (e: Exception){
            return false
        }
    }

    fun getClaims(token: Token): Claims {
        val secretKey = when(token.type){
            TokenType.ACCESS -> ConfigProperties.getProperty("JWT_SECRET")
            TokenType.REFRESH -> ConfigProperties.getProperty("JWT_SECRET_REFRESH")
        }
        return Jwts
            .parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(secretKey.toByteArray()))
            .build()
            .parseClaimsJws(token.value)
            .body
    }

}