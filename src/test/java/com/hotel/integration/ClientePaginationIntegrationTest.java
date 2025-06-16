package com.hotel.integration;

import com.hotel.model.Cliente;
import com.hotel.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientePaginationIntegrationTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        for (int i = 1; i <= 5; i++) {
            Cliente cliente = new Cliente();
            cliente.setNombre("Cliente " + i + " " + UUID.randomUUID());
            cliente.setEmail("cliente" + i + UUID.randomUUID() + "@mail.com");
            cliente.setDni("dni" + ThreadLocalRandom.current().nextInt(100000, 999999));
            clienteRepository.save(cliente);
        }
    }

    @Test
    void testPaginacionClientes() {
        Page<Cliente> page0 = clienteRepository.findByActivoTrue(PageRequest.of(0, 2));
        assertEquals(2, page0.getContent().size());
        assertTrue(page0.getTotalElements() >= 5);

        Page<Cliente> page1 = clienteRepository.findByActivoTrue(PageRequest.of(1, 2));
        assertEquals(2, page1.getContent().size());
    }
}