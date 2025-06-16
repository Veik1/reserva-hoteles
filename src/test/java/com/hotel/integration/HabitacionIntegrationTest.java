package com.hotel.integration;

import com.hotel.model.Usuario;
import com.hotel.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HabitacionIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void ensureAdminExists() {
        String adminHash = "$2a$10$i.NwHvQQGMjFfwnhexDKOezRNPBpGhD0cH5Fv6MoNwKd80Xse0emK";
        usuarioRepository.findByUsername("admin").orElseGet(() -> {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(adminHash);
            admin.setRole("ADMIN");
            admin.setActivo(true);
            return usuarioRepository.save(admin);
        });
    }

    private String url(String path) {
        return path.startsWith("/") ? path : "/" + path;
    }

    @SuppressWarnings("unchecked")
    private String obtenerToken(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.postForEntity(url("/api/auth/login"), request,
                (Class<Map<String, Object>>) (Class<?>) Map.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "Response body is null");
        Map<String, Object> responseBody = response.getBody();
        assertNotNull(responseBody, "Response body is null");
        return (String) responseBody.get("token");
    }

    private HttpHeaders authHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return headers;
    }

    @Test
    void adminPuedeVerHabitaciones() {
        String adminToken = obtenerToken("admin", "admin");

        HttpEntity<Void> adminRequest = new HttpEntity<>(authHeaders(adminToken));
        ResponseEntity<String> responseAdmin = restTemplate.exchange(
                url("/api/habitaciones"), HttpMethod.GET, adminRequest, String.class);
        assertEquals(HttpStatus.OK, responseAdmin.getStatusCode());
    }
}