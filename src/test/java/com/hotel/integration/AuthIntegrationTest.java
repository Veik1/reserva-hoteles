package com.hotel.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import com.hotel.config.TestSecurityConfig;
import org.springframework.context.annotation.Import;
import com.hotel.model.Usuario;
import com.hotel.repository.UsuarioRepository;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestSecurityConfig.class)
class AuthIntegrationTest {

        @LocalServerPort
        private int port;

        @Autowired
        private TestRestTemplate restTemplate;

        @Autowired
        private UsuarioRepository usuarioRepository;

        @BeforeEach
        void ensureUsuarioExists() {
                Usuario usuario = usuarioRepository.findByUsername("usuario");
                if (usuario == null) {
                        usuario = new Usuario();
                        usuario.setUsername("usuario");
                        usuario.setPassword("1234");
                        usuario.setRoles(Set.of("USER"));
                        usuario.setEnabled(true);
                        usuarioRepository.save(usuario);
                }
        }

        private String url(String path) {
                return "http://localhost:" + port + path;
        }

        @Test
        void loginOk() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                String loginBody = "{\"username\":\"usuario\",\"password\":\"1234\"}";
                HttpEntity<String> loginRequest = new HttpEntity<>(loginBody, headers);
                ResponseEntity<Map<String, Object>> loginResponse = restTemplate.exchange(
                                url("/api/auth/login"),
                                HttpMethod.POST,
                                loginRequest,
                                new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {
                                });
                assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
                Map<String, Object> body = loginResponse.getBody();
                assertNotNull(body);
                assertEquals("Login exitoso", body.get("message"));
        }
}