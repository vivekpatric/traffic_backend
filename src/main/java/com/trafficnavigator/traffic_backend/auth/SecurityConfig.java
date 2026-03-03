package com.trafficnavigator.traffic_backend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    @Bean
    public SecurityFilterChain security (HttpSecurity http) throws Exception{
                 http
                         .cors(cors->{})
                     .csrf(csrf->csrf.disable())
                     .sessionManagement(session ->
                             session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                     )
                         .securityContext(ctx -> ctx.requireExplicitSave(false))
                         .requestCache(cache ->cache.disable())
                         .authorizeHttpRequests(auth-> auth
                                 .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll() // ⭐ ADD
                                 .requestMatchers("/auth/**").permitAll()
                                 .anyRequest().authenticated()
                         )
                     .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

             return http.build();

    }
    @Bean
    PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);   // ⭐ REQUIRED for cookies

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }

}
