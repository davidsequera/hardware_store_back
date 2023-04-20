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
import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * A component that provides functionality to generate, verify, and extract claims from JSON Web Tokens (JWTs).
 */
@Component
class TokenComponent {

    /**
     * The expiration time for access tokens, in minutes.
     */
    val ACCESS_EXPIRATION: Long = 60

    /**
     * The expiration time for refresh tokens, in days.
     */
    val REFRESH_EXPIRATION: Long = 7

    /**
     * Generates a JWT for the given credential and token type.
     *
     * @param c the credential to generate the token for.
     * @param type the type of token to generate.
     *
     * @return the generated JWT, prepended with "Bearer ".
     */
    fun sign(c: Credential, type: TokenType): String? {
        val secretKey = when (type) {
            TokenType.ACCESS -> ConfigProperties.getProperty("JWT_SECRET")
            TokenType.REFRESH -> ConfigProperties.getProperty("JWT_SECRET_REFRESH")
        }
        val expiration: Long = when (type) {
            TokenType.ACCESS -> ACCESS_EXPIRATION
            TokenType.REFRESH -> REFRESH_EXPIRATION
        }
        val timeUnit = when (type) {
            TokenType.ACCESS -> ChronoUnit.MINUTES
            TokenType.REFRESH -> ChronoUnit.DAYS
        }

        val token = Jwts
            .builder()
            .setId(UUID.randomUUID().toString())
            .setSubject(c.id)
            .claim("email", c.email)
            .claim("type", type.toString())
            .claim("authorities", "ROLE_USER")
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plus(expiration, timeUnit)))
            .signWith(
                Keys.hmacShaKeyFor(secretKey.toByteArray()),
                SignatureAlgorithm.HS512
            ).compact()
        return "Bearer $token"
    }

    /**
     * Verifies the authenticity of the given token.
     *
     * @param token the token to verify.
     *
     * @return `true` if the token is verified, `false` otherwise.
     */
    fun verify(token: Token): Boolean {
        val verified: Boolean
        val secretKey = when (token.type) {
            TokenType.ACCESS -> ConfigProperties.getProperty("JWT_SECRET")
            TokenType.REFRESH -> ConfigProperties.getProperty("JWT_SECRET_REFRESH")
        }
        verified = try {
            // Claims
            Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.toByteArray()))
                .build()
                .parseClaimsJws(token.value)
                .body
            true
        } catch (e: Exception) {
            false
        }
        return verified
    }

    /**
     * Checks if the given token is currently valid.
     *
     * @param token the token to check.
     *
     * @return `true` if the token is currently valid, `false` otherwise.
     */

    fun current(token: Token): Boolean {
        val secretKey = when(token.type){
            TokenType.ACCESS -> ConfigProperties.getProperty("JWT_SECRET")
            TokenType.REFRESH -> ConfigProperties.getProperty("JWT_SECRET_REFRESH")
        }
        return try {
            val claims = Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.toByteArray()))
                .build()
                .parseClaimsJws(token.value)
                .body

            !claims.expiration.before(Date())
        }catch (e: Exception){
            false
        }
    }
    /**
     * Retrieves the claims contained in a JWT token.
     *
     * @param token the JWT token to get the claims from
     *
     * @return a Claims object containing the claims from the token
     */
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