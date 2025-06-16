package com.hotel.integration;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Map;

public class TestAuthUtils {
    public static boolean loginOk(TestRestTemplate restTemplate, String baseUrl, String username, String password) {
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
        return response.getStatusCode() == HttpStatus.OK;
    }
}