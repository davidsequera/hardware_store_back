package com.hardware.tools.configuration;

import com.hardware.tools.configuration.JWT.JWTAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Map to all endpoints
                .allowedOriginPatterns("*") // Allow all origins, you can specify specific origins here
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
                .allowedHeaders("*") // Allow all headers, you can specify specific headers here
                .allowCredentials(true); // Allow credentials (e.g. cookies) to be sent
    }

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
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection
                // Allow unauthenticated access to /graphiql
                .authorizeHttpRequests(requests -> requests.requestMatchers("/graphiql").permitAll())
                // Add the JWT authentication filter
                .addFilterAfter(new JWTAuthFilter(), RequestHeaderAuthenticationFilter.class)
                // Require authentication for /graphql
                .authorizeHttpRequests(requests -> requests.requestMatchers("/graphql").permitAll())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // Use stateless sessions
                .httpBasic(withDefaults()) // Use basic authentication
                .build();
    }
}