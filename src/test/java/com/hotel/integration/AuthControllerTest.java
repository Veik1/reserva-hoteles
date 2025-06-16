package com.hotel.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.controller.AuthController;
import com.hotel.model.RefreshToken;
import com.hotel.repository.UsuarioRepository;
import com.hotel.security.JwtUtil;
import com.hotel.service.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private AuthenticationManager authenticationManager;

        @MockBean
        private UserDetailsService userDetailsService;

        @MockBean
        private UsuarioRepository usuarioRepository;

        @MockBean
        private RefreshTokenService refreshTokenService;

        @MockBean
        private PasswordEncoder passwordEncoder;

        @MockBean
        private JwtUtil jwtUtil;

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Test
        void loginOk() throws Exception {
                // Mock AuthenticationManager to authenticate successfully
                when(authenticationManager.authenticate(any())).thenReturn(
                                UsernamePasswordAuthenticationToken.authenticated(
                                                "admin", "admin", List.of(() -> "ROLE_ADMIN")));

                // Mock UserDetailsService to return a valid user
                UserDetails userDetails = User.withUsername("admin")
                                .password("admin")
                                .roles("ADMIN")
                                .build();
                when(userDetailsService.loadUserByUsername("admin")).thenReturn(userDetails);

                // Mock PasswordEncoder to always match
                when(passwordEncoder.matches(any(), any())).thenReturn(true);

                // Mock JwtUtil to return a fixed token
                when(jwtUtil.generateToken(any(), any())).thenReturn("mock-jwt-token");

                // Mock RefreshTokenService to return a fixed refresh token
                RefreshToken mockRefreshToken = new RefreshToken();
                mockRefreshToken.setToken("mock-refresh-token");
                when(refreshTokenService.createRefreshToken(any(), anyLong())).thenReturn(mockRefreshToken);

                mockMvc.perform(post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                                java.util.Map.of("username", "admin", "password", "admin"))))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.username").value("admin"))
                                .andExpect(jsonPath("$.role").value("ADMIN"))
                                .andExpect(jsonPath("$.token").value("mock-jwt-token"))
                                .andExpect(jsonPath("$.refreshToken").value("mock-refresh-token"));
        }
}