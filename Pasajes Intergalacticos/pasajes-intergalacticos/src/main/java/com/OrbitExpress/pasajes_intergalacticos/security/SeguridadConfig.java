package com.OrbitExpress.pasajes_intergalacticos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SeguridadConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable(); // Desactiva la protección de encabezados para H2 Console
        http
                .cors() // Habilita CORS
                .and()
                .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll() // Permite acceso a todas las rutas sin autenticación
                )
                .csrf().disable(); // Desactiva CSRF para facilitar pruebas en Postman
        return http.build();
    }

    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();
        configuration.addAllowedOrigin("http://127.0.0.1:5500"); // Permite solicitudes desde el frontend
        configuration.addAllowedMethod("*"); // Permite todos los métodos HTTP (GET, POST, etc.)
        configuration.addAllowedHeader("*"); // Permite todos los encabezados
        configuration.setAllowCredentials(true); // Permite el envío de cookies o credenciales
        org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica la configuración a todas las rutas
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
