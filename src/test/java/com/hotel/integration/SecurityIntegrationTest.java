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

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SecurityIntegrationTest {

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
                        admin.setRoles(Set.of("ADMIN"));
                        admin.setEnabled(true);
                        usuarioRepository.save(admin);
                }
        }

        private String url(String path) {
                return "http://localhost:" + port + (path.startsWith("/") ? path : "/" + path);
        }

        @Test
        void adminPuedeAutenticarse() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                String body = "{\"username\":\"admin\",\"password\":\"admin\"}";
                HttpEntity<String> request = new HttpEntity<>(body, headers);

                ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                                url("/api/auth/login"),
                                HttpMethod.POST,
                                request,
                                new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {
                                });

                assertEquals(HttpStatus.OK, response.getStatusCode());
                Map<String, Object> responseBody = response.getBody();
                assertNotNull(responseBody, "Response body is null");
                assertEquals("Login exitoso", responseBody.get("message"));
        }
}