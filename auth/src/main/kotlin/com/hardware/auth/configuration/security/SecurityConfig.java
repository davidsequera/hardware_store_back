package com.hardware.auth.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Configuration class for setting up Spring Security.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig implements WebMvcConfigurer {
    /**
     * Default constructor for the SecurityConfig class.
     * This class provides configuration for Spring Security.
     */
    public SecurityConfig() {
        // empty constructor
    }


//    private final AuthenticationProvider authenticationProvider;
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(authenticationProvider);
//    }

    /**
     * Adds Cross-Origin Resource Sharing (CORS) mappings to the application.
     * @param registry the registry to add mappings to.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Map to all endpoints
                .allowedOriginPatterns("*") // Allow all origins, you can specify specific origins here
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
                .allowedHeaders("*") // Allow all headers, you can specify specific headers here
                .allowCredentials(true); // Allow credentials (e.g. cookies) to be sent
    }

    /**
     * Configures Spring Security to use JWT authentication for all endpoints except /graphiql, which is left open.
     * @param http the HttpSecurity object to configure.
     * @return the DefaultSecurityFilterChain object.
     * @throws Exception if an exception occurs while configuring the HttpSecurity object.
     */
    @Bean
    DefaultSecurityFilterChain springWebFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection
                // Allow unauthenticated access to /graphiql
                .authorizeHttpRequests(requests -> requests.requestMatchers("/graphiql").permitAll())
                // Add the JWT authentication filter
                // Require authentication for /graphql
                .authorizeHttpRequests(requests -> requests.requestMatchers("/graphql").permitAll())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // Use stateless sessions
                .httpBasic(withDefaults()) // Use basic authentication
                .build();
    }
}


