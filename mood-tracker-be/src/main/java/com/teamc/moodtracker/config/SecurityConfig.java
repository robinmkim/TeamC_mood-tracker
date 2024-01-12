package com.teamc.moodtracker.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                // 인증 설정
                                .cors(cors -> cors.configurationSource(myCorsConfigurationSource()))
                                // 인가 설정
                                .authorizeRequests(authorize -> authorize.anyRequest().permitAll());
                return http.build();
        }

        CorsConfigurationSource myCorsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowCredentials(true);
                configuration.setAllowedOrigins(Arrays.asList("http://192.168.0.219:8081/", "http://localhost:8081/"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                configuration.setAllowedHeaders(Arrays.asList("*"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}
