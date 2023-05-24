package com.hardware.user.configuration.JWT;


import com.hardware.user.configuration.ConfigProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class TokenComponentTest {

    @Autowired
    private TokenComponent tokenComponent;
    /*
     * This function checks if the token is valid
     *
     * @param token el token que se va a validar
     * @return Claims si el token no esta
     * */

    public  static String subject = "test";
    public static String signTokenTesting() throws IOException {
        try {
            ConfigProperties configProperties = ConfigProperties.getInstance();
            String secretKey =configProperties.getProperty("JWT_SECRET");
            Date expirationDate = new Date(System.currentTimeMillis() + 86400000);
            String token = Jwts.builder()
                    .setSubject(subject)
                    .claim("email", "test@mail.com")
                    .claim("authorities", "ROLE_USER")
                    .setExpiration(expirationDate)
                    .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS512)
                    .compact();
            System.out.println("[sign] Token signed "+ token);
            return token;
        } catch (IOException e) {
            System.out.println("[sign] Error opening config file " + e.getMessage());
            throw e;
        }
    }


    public static Stream<String> tokenProvider() throws IOException {
        // Return a stream of objects to be used as test data

        return Stream.of(
                signTokenTesting(),
                "1.2.3",
                "123"
        );
    }
    @ParameterizedTest
    @MethodSource("tokenProvider")
    void testVerify(String token) {
        try{
           Claims claims =  tokenComponent.verify(token);
           String testSubject = claims.getSubject();
           assertEquals(subject, testSubject, "Subject is not the same");
        }
        catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
//            System.out.println("[verify] Error verifying token " + e.getMessage());
            assertNotNull(e.getMessage());
        }catch (IOException e){
            System.out.println("[verify] Error opening config file " + e.getMessage());
            assert false;
        }
    }

//        throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, io.jsonwebtoken.security.SignatureException, IllegalArgumentException, IOException
}

