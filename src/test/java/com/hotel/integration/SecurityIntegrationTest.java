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
                return "http://localhost:" + port + (path.startsWith("/") ? path : "/" + path);
        }

        protected String obtenerToken(String username, String password) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                String body = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
                HttpEntity<String> request = new HttpEntity<>(body, headers);

                ResponseEntity<java.util.Map<String, Object>> response = restTemplate.exchange(
                                url("/api/auth/login"),
                                HttpMethod.POST,
                                request,
                                new org.springframework.core.ParameterizedTypeReference<java.util.Map<String, Object>>() {
                                });
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody(), "Response body is null");
                java.util.Map<String, Object> bodyMap = response.getBody();
                assertNotNull(bodyMap, "Response body is null");
                Object tokenObj = bodyMap.get("token");
                assertNotNull(tokenObj, "Token is null in response body");
                return (String) tokenObj;
        }

        protected HttpHeaders authHeaders(String token) {
                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(token);
                return headers;
        }

        // Ejemplo de test usando admin
        @Test
        void adminPuedeAutenticarse() {
                String token = obtenerToken("admin", "admin");
                assertNotNull(token);
                assertFalse(token.isEmpty());
        }
}