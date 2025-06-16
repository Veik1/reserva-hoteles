package com.hotel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

        @Value("${spring.profiles.active:}")
        private String activeProfile;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .authorizeHttpRequests(auth -> auth
                                                // Swagger solo en dev
                                                .requestMatchers("/swagger-ui.html", "/swagger-ui/**",
                                                                "/v3/api-docs/**")
                                                .permitAll()
                                                // Endpoints públicos (ajusta según tu app)
                                                .requestMatchers("/public/**").permitAll()
                                                // Endpoints protegidos
                                                .requestMatchers("/api/hoteles/**").hasAnyRole("ADMIN", "USER")
                                                .requestMatchers("/api/habitaciones/**").hasRole("ADMIN")
                                                .requestMatchers("/api/reservas/**").hasAnyRole("ADMIN", "USER")
                                                .anyRequest().authenticated())
                                .httpBasic(org.springframework.security.config.Customizer.withDefaults())
                // Opcional: habilita login form
                // .formLogin(form -> form.permitAll())
                ;

                // Permite frames solo en dev para H2-console
                if ("dev".equals(activeProfile)) {
                        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
                }

                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*"));
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(List.of("*"));
                configuration.setAllowCredentials(false);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public UserDetailsService users(PasswordEncoder passwordEncoder) {
                UserDetails user = User.builder()
                                .username("usuario")
                                .password(passwordEncoder.encode("1234"))
                                .roles("USER")
                                .build();
                UserDetails admin = User.builder()
                                .username("admin")
                                .password(passwordEncoder.encode("admin"))
                                .roles("ADMIN")
                                .build();
                return new InMemoryUserDetailsManager(user, admin);
        }
}