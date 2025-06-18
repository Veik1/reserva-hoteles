package com.hotel.integration;

import com.hotel.model.Usuario;
import com.hotel.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.hotel.config.TestSecurityConfig;
import org.springframework.context.annotation.Import;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class AuthControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private UsuarioRepository usuarioRepository;

        @BeforeEach
        void ensureAdminExists() {
                Usuario admin = usuarioRepository.findByUsername("admin");
                if (admin == null) {
                        admin = new Usuario();
                        admin.setUsername("admin");
                        admin.setPassword("admin");
                        admin.setRole("ADMIN");
                        admin.setEnabled(true);
                        admin.setCliente(null);
                        usuarioRepository.save(admin);
                }
        }

        @Test
        void loginOk() throws Exception {
                mockMvc.perform(post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"username\":\"admin\",\"password\":\"admin\"}"))
                                .andExpect(status().isOk());
        }
}