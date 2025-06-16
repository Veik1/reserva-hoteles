package com.hotel.repository;

import com.hotel.model.RefreshToken;
import com.hotel.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUsuario(Usuario usuario);
    void deleteByToken(String token); 
}