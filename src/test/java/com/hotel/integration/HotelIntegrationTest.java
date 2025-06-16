package com.hotel.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.model.Usuario;
import com.hotel.repository.UsuarioRepository;
import com.hotel.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(JwtUtil.class)
class HotelIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private UsuarioRepository usuarioRepository;

        private String adminJwt;

        @BeforeEach
        void setUp() throws Exception {
                // Asegura que el usuario admin exista en la base de datos de test con el mismo
                // hash que tu seed
                String adminHash = "$2a$10$i.NwHvQQGMjFfwnhexDKOezRNPBpGhD0cH5Fv6MoNwKd80Xse0emK";
                usuarioRepository.findByUsername("admin").orElseGet(() -> {
                        Usuario admin = new Usuario();
                        admin.setUsername("admin");
                        admin.setPassword(adminHash);
                        admin.setRole("ADMIN");
                        admin.setActivo(true);
                        return usuarioRepository.save(admin);
                });

                var loginRequest = objectMapper.writeValueAsString(
                                java.util.Map.of("username", "admin", "password", "admin"));
                var result = mockMvc.perform(post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(loginRequest))
                                .andReturn();
                var response = result.getResponse().getContentAsString();
                var json = objectMapper.readTree(response);
                adminJwt = json.get("token").asText();
        }

        @Test
        void crearHotelComoAdminDebeFuncionar() throws Exception {
                var hotel = java.util.Map.of(
                                "nombre", "Hotel Test",
                                "ciudad", "Ciudad Test",
                                "direccion", "Calle 123",
                                "activo", true);

                mockMvc.perform(post("/api/hoteles")
                                .header("Authorization", "Bearer " + adminJwt)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(hotel)))
                                .andExpect(status().isCreated());
        }

        @Test
        void crearHotelSinNombreDebeFallar() throws Exception {
                var hotel = java.util.Map.of(
                                "ciudad", "Ciudad Test",
                                "direccion", "Calle 123",
                                "activo", true);

                mockMvc.perform(post("/api/hoteles")
                                .header("Authorization", "Bearer " + adminJwt)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(hotel)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void crearHotelSinTokenDebeFallar() throws Exception {
                var hotel = java.util.Map.of(
                                "nombre", "Hotel Sin Token",
                                "ciudad", "Ciudad Test",
                                "direccion", "Calle 123",
                                "activo", true);

                mockMvc.perform(post("/api/hoteles")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(hotel)))
                                .andExpect(status().isForbidden());
        }
}