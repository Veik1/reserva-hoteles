package com.hotel.service;

import com.hotel.model.RefreshToken;
import com.hotel.model.Usuario;
import com.hotel.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(Usuario usuario, long expirySeconds) {
        RefreshToken token = new RefreshToken();
        token.setUsuario(usuario);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusSeconds(expirySeconds));
        return refreshTokenRepository.save(token);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public void deleteByUsuario(Usuario usuario) {
        refreshTokenRepository.deleteByUsuario(usuario);
    }

    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    public boolean isExpired(RefreshToken token) {
        return token.getExpiryDate().isBefore(LocalDateTime.now());
    }
}