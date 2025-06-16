package com.hotel.integration;

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

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void accesoHabitacionesSoloAdmin() {
        // Usuario USER no puede acceder a habitaciones (solo ADMIN)
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("usuario", "1234")
                .getForEntity(url("/api/habitaciones"), String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        // Usuario ADMIN sí puede acceder
        ResponseEntity<String> responseAdmin = restTemplate
                .withBasicAuth("admin", "admin")
                .getForEntity(url("/api/habitaciones"), String.class);
        assertNotEquals(HttpStatus.FORBIDDEN, responseAdmin.getStatusCode());
    }

    @Test
    void accesoReservasAdminYUser() {
        // Ambos pueden acceder a reservas
        ResponseEntity<String> responseUser = restTemplate
                .withBasicAuth("usuario", "1234")
                .getForEntity(url("/api/reservas"), String.class);
        assertNotEquals(HttpStatus.FORBIDDEN, responseUser.getStatusCode());

        ResponseEntity<String> responseAdmin = restTemplate
                .withBasicAuth("admin", "admin")
                .getForEntity(url("/api/reservas"), String.class);
        assertNotEquals(HttpStatus.FORBIDDEN, responseAdmin.getStatusCode());
    }

    @Test
    void accesoHotelesAdminYUser() {
        // Ambos pueden acceder a hoteles
        ResponseEntity<String> responseUser = restTemplate
                .withBasicAuth("usuario", "1234")
                .getForEntity(url("/api/hoteles"), String.class);
        assertNotEquals(HttpStatus.FORBIDDEN, responseUser.getStatusCode());

        ResponseEntity<String> responseAdmin = restTemplate
                .withBasicAuth("admin", "admin")
                .getForEntity(url("/api/hoteles"), String.class);
        assertNotEquals(HttpStatus.FORBIDDEN, responseAdmin.getStatusCode());
    }

    @Test
    void accesoSinAuth() {
        // Sin autenticación, debe ser 401
        ResponseEntity<String> response = restTemplate
                .getForEntity(url("/api/hoteles"), String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}