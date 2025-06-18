package com.hotel.integration;

import com.hotel.model.Usuario;
import com.hotel.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import jakarta.annotation.PostConstruct;

@TestComponent
public class UsuarioSeed {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostConstruct
    public void seed() {
        if (usuarioRepository.findByUsername("admin") == null) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setRole("ADMIN"); // Cambiado aquí
            admin.setEnabled(true);
            usuarioRepository.save(admin);
        }
    }
}