package com.hardware.tools.configuration;

import com.hardware.tools.configuration.JWT.JWTAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {


//    private final AuthenticationProvider authenticationProvider;
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(authenticationProvider);
//    }


    @Bean
    DefaultSecurityFilterChain springWebFilterChain(HttpSecurity http) throws Exception {
        return  http
                .csrf(c -> c.disable())
                // Allow unauthenticated access to /graphiql
                .authorizeHttpRequests(requests -> requests.requestMatchers("/graphiql").permitAll())
                // Add the JWT authentication filter
                .addFilterAfter(new JWTAuthFilter(), RequestHeaderAuthenticationFilter.class)
                // Require authentication for /graphql
                .authorizeHttpRequests(requests -> requests.requestMatchers("/graphql").permitAll())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .httpBasic(withDefaults())
                .build();
    }
}