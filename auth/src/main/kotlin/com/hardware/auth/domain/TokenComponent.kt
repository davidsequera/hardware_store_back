package com.hardware.auth.domain

import com.hardware.auth.configuration.ConfigProperties
import com.hardware.auth.domain.entities.Credential
import com.hardware.auth.domain.entities.Token
import com.hardware.auth.domain.entities.TokenType
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*


@Component
class TokenComponent {


    fun sign(c: Credential, type : TokenType): String? {
        val secretKey = when(type){
            TokenType.ACCESS -> ConfigProperties.getProperty("JWT_SECRET")
            TokenType.REFRESH -> ConfigProperties.getProperty("JWT_SECRET_REFRESH")
        }
        val expiration = when(type){
            TokenType.ACCESS -> 600000
            TokenType.REFRESH -> 3600000
        }
        val token = Jwts
            .builder()
            .setId(UUID.randomUUID().toString())
            .setSubject(c.id)
            .claim("email", c.email)
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
            val claims = Jwts
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

         val expiration = claims.expiration
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