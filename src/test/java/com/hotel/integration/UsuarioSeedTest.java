package com.hotel.integration;

import com.hotel.repository.UsuarioRepository;
import com.hotel.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsuarioSeedTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    void usuariosDeSeedExistenYPasswordEsBCrypt() {
        Optional<Usuario> adminOpt = usuarioRepository.findByUsername("admin");
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername("usuario");

        assertAll("Verifica existencia de usuarios de seed",
            () -> assertTrue(adminOpt.isPresent(), "El usuario 'admin' no existe en la base de test"),
            () -> assertTrue(usuarioOpt.isPresent(), "El usuario 'usuario' no existe en la base de test")
        );

        Usuario admin = adminOpt.get();
        Usuario usuario = usuarioOpt.get();

        assertAll("Verifica passwords encriptados correctamente",
            () -> assertTrue(encoder.matches("admin", admin.getPassword()), "El password de 'admin' no es válido"),
            () -> assertTrue(encoder.matches("1234", usuario.getPassword()), "El password de 'usuario' no es válido")
        );
    }
}