package com.hardware.user.configuration.JWT;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;


public class JWTAuthFilter extends OncePerRequestFilter {

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private final TokenComponent tokenComponent = new TokenComponent();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        try {

            validateAuthHeader(request);
            Claims claims = validateToken(request);
            setUpSpringAuthentication(claims, request);

        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | NullPointerException e) {
            System.out.println("Error en el filtro " + e.getMessage());
        }

        chain.doFilter(request, response);
    }

    /**
     * Metodo para autenticarnos dentro del flujo de Spring
     *
     * @param claims los detalles del token como el usuario, el rol, etc
     * @param request la peticion HTTP
     */
    private void setUpSpringAuthentication(Claims claims, HttpServletRequest request) throws NullPointerException{
        String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
        String authorities = claims.get("authorities",String.class);
        Stream<String> authorities_stream = Stream.of(authorities.split(Pattern.quote(",")));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(),jwtToken, authorities_stream.map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private void validateAuthHeader (HttpServletRequest request) throws MalformedJwtException{
        String authenticationHeader = request.getHeader(HEADER);
        if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX) ){
            throw new MalformedJwtException("Missing or invalid Authorization header.");
        }
    }
    private Claims validateToken(HttpServletRequest request) throws IOException, ExpiredJwtException{
        String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");

        return tokenComponent.verify(jwtToken);
    }
}