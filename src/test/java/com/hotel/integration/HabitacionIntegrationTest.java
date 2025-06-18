package com.hotel.integration;

import com.hotel.model.Usuario;
import com.hotel.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import com.hotel.config.TestSecurityConfig;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestSecurityConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HabitacionIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void ensureAdminExists() {
        Usuario admin = usuarioRepository.findByUsername("admin");
        if (admin == null) {
            admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setRole("ADMIN"); // Cambiado aquí
            admin.setEnabled(true);
            admin.setCliente(null);
            usuarioRepository.save(admin);
        }
    }

    private String url(String path) {
        return "http://localhost:" + port + (path.startsWith("/") ? path : "/" + path);
    }

    @Test
    void adminPuedeVerHabitaciones() {
        // Realiza una petición GET al endpoint de habitaciones sin autenticación
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url("/api/habitaciones"),
                HttpMethod.GET,
                request,
                String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        // Puedes agregar más asserts según la respuesta esperada
    }
}