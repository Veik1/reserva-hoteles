package com.hotel.repository;

import com.hotel.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
    Optional<Usuario> findByUsernameAndEnabledTrue(String username);
}