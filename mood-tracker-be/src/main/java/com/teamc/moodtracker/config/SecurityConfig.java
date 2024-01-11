package com.teamc.moodtracker.config;

import com.teamc.moodtracker.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
        // JWT 토큰을 검증하는 필터
        @Autowired
        private JwtTokenFilter jwtAuthenticationFilter;

        <<<<<<<HEAD
        @Autowired
        private UserDetailsService userDetailsService;
        // 이 메소드는 DaoAuthenticationProvider 객체를 생성하고 구성
        // UserDetailsService와 PasswordEncoder를 설정하여 사용자 인증 정보를 관리한다.
        =======
        private final Environment env;>>>>>>>

        b170f72 (✨ 채팅 백엔드 구현)

        @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setUserDetailsService(userDetailsService);
                provider.setPasswordEncoder(passwordEncoder());
                return provider;
        }

        private final Environment env;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        AuthenticationManager authenticationManager(
                        AuthenticationConfiguration authenticationConfiguration) throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf((csrf) -> csrf.disable())
                                .cors(cors -> cors.configurationSource(myCorsConfigurationSource()))
                                .formLogin((login) -> login.disable())
                                .httpBasic((basic) -> basic.disable())
                                // HTTP 요청에 대한 보안 필터 체인을 구성
                                // .authorizeHttpRequests((auth) -> auth.anyRequest().permitAll())
                                .authorizeHttpRequests((auth) -> auth
                                                .requestMatchers("/api/auth/**").permitAll()
                                                .anyRequest().authenticated())
                                // JWT 토큰 필터 추가: JwtTokenFilter를 BasicAuthenticationFilter 전에 추가하여 JWT 토큰을 검증
                                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
                                // 세션 정책 설정: SessionCreationPolicy.STATELESS로 설정하여 세션 기반 인증을 사용하지 않도록 한다.
                                .sessionManagement((session) -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                // 로그아웃 기능 비활성화: 상태를 유지하지 않는 인증 방식에서는 로그아웃이 필요 없다.
                                .logout((logout) -> logout.disable());
                // 인증 설정
                // 인가 설정

                return http.build();
        }

        CorsConfigurationSource myCorsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                String allowedOriginsProperty = env.getProperty("allowed-origins");
                List<String> allowedOrigins = (allowedOriginsProperty != null)
                                ? Arrays.asList(allowedOriginsProperty.split(","))
                                : Collections.emptyList();
                System.out.println("allowedOrigins: " + allowedOrigins);
                configuration.setAllowCredentials(true);
                configuration.setAllowedOrigins(allowedOrigins);
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                configuration.setAllowedHeaders(Arrays.asList("*"));
                configuration.setAllowCredentials(true);
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }=======

        // JWT 토큰을 검증하는 필터
        @Autowired
        private JwtTokenFilter jwtAuthenticationFilter;

        <<<<<<<HEAD
        @Autowired
        private UserDetailsService userDetailsService;
        // 이 메소드는 DaoAuthenticationProvider 객체를 생성하고 구성
        // UserDetailsService와 PasswordEncoder를 설정하여 사용자 인증 정보를 관리한다.

    private final Environment env;>>>>>>>

        @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setUserDetailsService(userDetailsService);
                provider.setPasswordEncoder(passwordEncoder());
                return provider;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        AuthenticationManager authenticationManager(
                        AuthenticationConfiguration authenticationConfiguration) throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf((csrf) -> csrf.disable())
                                .cors(cors -> cors.configurationSource(myCorsConfigurationSource()))
                                .formLogin((login) -> login.disable())
                                .httpBasic((basic) -> basic.disable())
                                // HTTP 요청에 대한 보안 필터 체인을 구성
                                // .authorizeHttpRequests((auth) -> auth.anyRequest().permitAll())
                                .authorizeHttpRequests((auth) -> auth
                                                .requestMatchers("/api/auth/**").permitAll()
                                                .anyRequest().authenticated())
                                // JWT 토큰 필터 추가: JwtTokenFilter를 BasicAuthenticationFilter 전에 추가하여 JWT 토큰을 검증
                                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
                                // 세션 정책 설정: SessionCreationPolicy.STATELESS로 설정하여 세션 기반 인증을 사용하지 않도록 한다.
                                .sessionManagement((session) -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                // 로그아웃 기능 비활성화: 상태를 유지하지 않는 인증 방식에서는 로그아웃이 필요 없다.
                                .logout((logout) -> logout.disable());
                // 인증 설정
                // 인가 설정

                return http.build();
        }

    CorsConfigurationSource myCorsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);

        String allowedOriginsProperty = env.getProperty("allowed-origins");
        List<String> allowedOrigins = (allowedOriginsProperty != null)
                ? Arrays.asList(allowedOriginsProperty.split(","))
                : Collections.emptyList();
        System.out.println("allowedOrigins: " + allowedOrigins);
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }>>>>>>>5be0767 (✨ 채팅 백엔드 구현)
}
