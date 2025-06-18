package com.hotel.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.model.Usuario;
import com.hotel.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.hotel.config.TestSecurityConfig;
import org.springframework.context.annotation.Import;

@Import(TestSecurityConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
class HotelIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private UsuarioRepository usuarioRepository;

        @BeforeEach
        void setUp() {
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

        @Test
        void crearHotelComoAdminDebeFuncionar() throws Exception {
                var hotel = java.util.Map.of(
                                "nombre", "Hotel Test",
                                "ciudad", "Ciudad Test",
                                "direccion", "Calle 123",
                                "activo", true);

                mockMvc.perform(post("/api/hoteles")
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
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(hotel)))
                                .andExpect(status().isBadRequest());
        }
}