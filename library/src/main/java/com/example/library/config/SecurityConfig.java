package com.example.library.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
//                                .requestMatchers("/api/books/**").permitAll() // Allow unrestricted access to /api/books endpoints
                                .anyRequest().permitAll() // All other requests require authentication
                )
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .cors(cors -> cors.disable()) // Disable CORS protection
                .httpBasic(
                        httpBasic -> httpBasic.disable()
                );// Disable HTTP Basic Authentication
        return http.build();
    }
}
