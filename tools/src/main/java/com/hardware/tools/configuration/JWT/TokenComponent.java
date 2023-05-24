package com.hardware.tools.configuration.JWT;


import com.hardware.tools.configuration.ConfigProperties;
import io.jsonwebtoken.*;

import java.io.IOException;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class TokenComponent {
    /*
    * This function checks if the token is valid
    *
    * @param token el token que se va a validar
    * @return Claims si el token no esta
    * */
    public Claims verify(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, IOException {
        try {
            ConfigProperties configProperties = ConfigProperties.getInstance();
            String secretKey =configProperties.getProperty("JWT_SECRET");
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw e;
        }catch (IOException e){
            System.out.println("[validate] Error opening config file " + e.getMessage());
            throw e;
        }
    }
}
