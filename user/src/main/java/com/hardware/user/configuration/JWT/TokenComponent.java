package com.hardware.user.configuration.JWT;


import com.hardware.user.configuration.ConfigProperties;
import io.jsonwebtoken.*;

import java.io.IOException;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.dataloader.annotations.VisibleForTesting;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class TokenComponent {

    public Claims verify(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, io.jsonwebtoken.security.SignatureException, IllegalArgumentException, IOException {
        try {
            ConfigProperties configProperties = ConfigProperties.getInstance();
            String secretKey =configProperties.getProperty("JWT_SECRET");
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw e;
        }catch (IOException e){
            System.out.println("[verify] Error opening config file " + e.getMessage());
            throw e;
        }
    }
}
