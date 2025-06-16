package com.hotel.controller;

import com.hotel.dto.LoginRequest;
import com.hotel.model.Usuario;
import com.hotel.model.RefreshToken;
import com.hotel.repository.UsuarioRepository;
import com.hotel.security.JwtUtil;
import com.hotel.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginData) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsernameAndActivoTrue(loginData.getUsername());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Credenciales inválidas"));
        }
        Usuario usuario = usuarioOpt.get();
        if (!passwordEncoder.matches(loginData.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Credenciales inválidas"));
        }
        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRole());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(usuario, 604800); // 7 días
        return ResponseEntity
                .ok(new LoginResponse(token, refreshToken.getToken(), usuario.getUsername(), usuario.getRole()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {
        String refreshTokenStr = body.get("refreshToken");
        if (refreshTokenStr == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Refresh token requerido"));
        }
        Optional<RefreshToken> refreshTokenOpt = refreshTokenService.findByToken(refreshTokenStr);
        if (refreshTokenOpt.isEmpty() || refreshTokenService.isExpired(refreshTokenOpt.get())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Refresh token inválido o expirado"));
        }
        Usuario usuario = refreshTokenOpt.get().getUsuario();
        String newToken = jwtUtil.generateToken(usuario.getUsername(), usuario.getRole());
        return ResponseEntity.ok(Map.of("token", newToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> body) {
        String refreshTokenStr = body.get("refreshToken");
        if (refreshTokenStr == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Refresh token requerido"));
        }
        refreshTokenService.deleteByToken(refreshTokenStr);
        return ResponseEntity.ok(Map.of("message", "Logout exitoso"));
    }

    // Clase interna para la respuesta de login
    public static class LoginResponse {
        private String token;
        private String refreshToken;
        private String username;
        private String role;

        public LoginResponse(String token, String refreshToken, String username, String role) {
            this.token = token;
            this.refreshToken = refreshToken;
            this.username = username;
            this.role = role;
        }

        public String getToken() {
            return token;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public String getUsername() {
            return username;
        }

        public String getRole() {
            return role;
        }
    }
}