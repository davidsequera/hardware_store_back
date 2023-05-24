package com.hardware.tools.configuration;

import com.hardware.tools.configuration.JWT.JWTAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;

import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Map to all endpoints
                .allowedOriginPatterns("*") // Allow all origins, you can specify specific origins here
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
                .allowedHeaders("*") // Allow all headers, you can specify specific headers here
                .allowCredentials(true); // Allow credentials (e.g. cookies) to be sent
    }

    @Bean
    public JWTAuthFilter jwtAuthFilter() {
        return new JWTAuthFilter();
    }

    @Bean
    SecurityWebFilterChain webfluxSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/graphiql").permitAll()
                .pathMatchers("/graphql").permitAll()
                .and()
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .addFilterAfter(jwtAuthFilter(), SecurityWebFiltersOrder.HTTP_BASIC)
                .httpBasic(withDefaults())
                .build();
    }

}