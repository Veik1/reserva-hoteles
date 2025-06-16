package com.hotel.integration;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Map;

public class TestAuthUtils {
    public static String obtenerToken(TestRestTemplate restTemplate, String baseUrl, String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                baseUrl + "/api/auth/login",
                HttpMethod.POST,
                request,
                new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {
                });
        if (response.getStatusCode() != HttpStatus.OK)
            throw new RuntimeException("No se pudo loguear");
        Map<String, Object> bodyMap = response.getBody();
        if (bodyMap == null || !bodyMap.containsKey("token")) {
            throw new RuntimeException("No se pudo obtener el token de autenticación");
        }
        return (String) bodyMap.get("token");
    }

    public static HttpHeaders authHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return headers;
    }
}