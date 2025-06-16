package com.hotel.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void loginRefreshLogoutRevokeAllFlow() {
        // Login
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
        String token = (String) body.get("token");
        String refreshToken = (String) body.get("refreshToken");
        assertNotNull(token);
        assertNotNull(refreshToken);

        // Refresh
        String refreshBody = String.format("{\"refreshToken\":\"%s\"}", refreshToken);
        HttpEntity<String> refreshRequest = new HttpEntity<>(refreshBody, headers);
        ResponseEntity<Map<String, Object>> refreshResponse = restTemplate.exchange(
                url("/api/auth/refresh"),
                HttpMethod.POST,
                refreshRequest,
                new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {
                });
        assertEquals(HttpStatus.OK, refreshResponse.getStatusCode());
        Map<String, Object> refreshBodyMap = refreshResponse.getBody();
        assertNotNull(refreshBodyMap);
        String newToken = (String) refreshBodyMap.get("token");
        assertNotNull(newToken);
        assertNotEquals(token, newToken);

        // Logout (elimina refresh token)
        String logoutBody = String.format("{\"refreshToken\":\"%s\"}", refreshToken);
        HttpEntity<String> logoutRequest = new HttpEntity<>(logoutBody, headers);
        ResponseEntity<Map<String, Object>> logoutResponse = restTemplate.exchange(
                url("/api/auth/logout"),
                HttpMethod.POST,
                logoutRequest,
                new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {
                });
        assertEquals(HttpStatus.OK, logoutResponse.getStatusCode());
        Map<String, Object> logoutBodyMap = logoutResponse.getBody();
        assertNotNull(logoutBodyMap);
        assertEquals("Logout exitoso", logoutBodyMap.get("message"));

        // Refresh con el mismo refresh token debe fallar
        ResponseEntity<String> refreshFail = restTemplate.postForEntity(url("/api/auth/refresh"), refreshRequest,
                String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, refreshFail.getStatusCode());

        // Revoke all tokens for user
        String revokeBody = "{\"username\":\"usuario\"}";
        HttpEntity<String> revokeRequest = new HttpEntity<>(revokeBody, headers);
        ResponseEntity<Map<String, Object>> revokeResponse = restTemplate.exchange(
                url("/api/auth/revoke-all"),
                HttpMethod.POST,
                revokeRequest,
                new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {
                });
        assertEquals(HttpStatus.OK, revokeResponse.getStatusCode());
        Map<String, Object> revokeBodyMap = revokeResponse.getBody();
        assertNotNull(revokeBodyMap);
        assertEquals("Todos los refresh tokens revocados", revokeBodyMap.get("message"));
    }
}