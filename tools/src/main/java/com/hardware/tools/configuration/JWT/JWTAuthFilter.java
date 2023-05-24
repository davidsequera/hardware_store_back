package com.hardware.tools.configuration.JWT;

import com.hardware.tools.domain.exceptions.NoHeaderAuthFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;


import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JWTAuthFilter implements WebFilter {

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private final TokenComponent tokenComponent = new TokenComponent();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        try {
            validateAuthHeader(exchange.getRequest());
            Claims claims = validateToken(exchange.getRequest());


            //Si el token es válido, lo agregamos al contexto de Spring
            var auth = getSpringAuthentication(exchange.getRequest(), claims);
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException | NoHeaderAuthFoundException e) {
//            System.out.println("Error en el filtro " + e.getMessage());
        }catch (NullPointerException | IOException e){
            System.out.println("[filter] Error: " + e.getMessage());
        }
        return chain.filter(exchange);
    }


    /**
     *
     * Metodo para autenticarnos dentro del flujo de Spring
     * @param claims los detalles del token como el usuario, el rol, etc
     * @param request la peticion HTTP
     */
    private Authentication getSpringAuthentication(ServerHttpRequest request, Claims claims) {
        String jwtToken = getToken(request);
        String authorities = claims.get("authorities",String.class);

        Stream<String> authorities_stream = Stream.of(authorities.split(Pattern.quote(",")));

        return new UsernamePasswordAuthenticationToken(claims.getSubject(),jwtToken, authorities_stream.map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }

    private void validateAuthHeader (ServerHttpRequest request) throws NoHeaderAuthFoundException {
        String authenticationHeader = request.getHeaders().getFirst(HEADER);
        if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX) ){
            throw new NoHeaderAuthFoundException("Missing or invalid Authorization header.");
        }
    }
    private Claims validateToken(ServerHttpRequest request) throws ExpiredJwtException, IOException, SignatureException, MalformedJwtException, UnsupportedJwtException {
        String jwtToken = getToken(request);
        return tokenComponent.verify(jwtToken);
    }

    private String getToken(ServerHttpRequest request){
        try {
            return Objects.requireNonNull(request.getHeaders().getFirst(HEADER)).replace(PREFIX, "");
        }catch (NullPointerException e){
            throw new NullPointerException("[getToken] Token no encontrado");
        }
    }


}
