package com.hotel.controller;

import com.hotel.model.Cliente;
import com.hotel.model.Usuario;
import com.hotel.repository.UsuarioRepository;
import com.hotel.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> data) {
        String nombre = data.get("nombre");
        String email = data.get("email");
        String dni = data.get("dni");
        String password = data.get("password");

        if (nombre == null || email == null || dni == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Faltan datos obligatorios"));
        }

        if (clienteRepository.findByEmail(email) != null) {
            return ResponseEntity.badRequest().body(Map.of("error", "El email ya está registrado"));
        }
        if (clienteRepository.findByDni(dni) != null) {
            return ResponseEntity.badRequest().body(Map.of("error", "El DNI ya está registrado"));
        }
        if (usuarioRepository.findByUsername(email) != null) {
            return ResponseEntity.badRequest().body(Map.of("error", "El usuario ya existe"));
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setDni(dni);
        cliente.setActivo(true);
        cliente = clienteRepository.save(cliente);

        Usuario usuario = new Usuario();
        usuario.setUsername(email); // El email es el username
        usuario.setPassword(password);
        usuario.setRole("USER");
        usuario.setActivo(true);
        usuario.setEnabled(true);
        usuario.setCliente(cliente);
        usuarioRepository.save(usuario);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Registro exitoso");
        response.put("cliente_id", cliente.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null || !password.equals(usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales inválidas"));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login exitoso");
        response.put("role", usuario.getRole());
        response.put("cliente_id", usuario.getCliente() != null ? usuario.getCliente().getId() : null);

        return ResponseEntity.ok(response);
    }
}