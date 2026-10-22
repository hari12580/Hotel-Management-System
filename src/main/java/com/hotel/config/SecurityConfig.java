package com.hotel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .authorizeHttpRequests(auth -> auth

            	    .requestMatchers(
            	        "/api/auth/register",
            	        "/api/auth/login"
            	    ).permitAll()

            	    .requestMatchers("/api/admin/**")
            	    .hasRole("ADMIN")

            	    .requestMatchers("/api/rooms/**")
            	    .hasRole("ADMIN")

            	    .requestMatchers("/api/customers/**")
            	    .hasRole("ADMIN")

            	    .requestMatchers("/api/bookings/**")
            	    .hasRole("ADMIN")

            	    .requestMatchers("/api/checkout/**")
            	    .hasRole("ADMIN")

            	    .anyRequest().authenticated()
            	)

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}