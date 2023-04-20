package com.hardware.tools.configuration.JWT;


import com.hardware.tools.configuration.ConfigProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class TokenComponent {


    public boolean verify(String token) {
        boolean verified;
        try {
            ConfigProperties configProperties = ConfigProperties.getInstance();
            String secretKey =configProperties.getProperty("JWT_SECRET");
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            verified = true;
        } catch (Exception e) {
            verified = false;
        }
        return verified;
    }

    public boolean current(String token) {
        try {
            ConfigProperties configProperties = ConfigProperties.getInstance();
            String secretKey =configProperties.getProperty("JWT_SECRET");
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            Date expiration = claims.getExpiration();
            return !expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaims(String token) throws IOException, ExpiredJwtException {
        ConfigProperties configProperties = ConfigProperties.getInstance();
        String secretKey =configProperties.getProperty("JWT_SECRET");
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
