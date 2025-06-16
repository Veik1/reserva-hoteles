package com.hotel.integration;

import com.hotel.model.Cliente;
import com.hotel.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClienteSoftDeleteIntegrationTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void softDeleteCliente() {
        Cliente cliente = new Cliente();
        cliente.setNombre("SoftDelete Cliente " + UUID.randomUUID());
        cliente.setEmail("soft" + UUID.randomUUID() + "@cliente.com");
        cliente.setDni("dni" + ThreadLocalRandom.current().nextInt(100000, 999999));
        clienteRepository.save(cliente);

        cliente.setActivo(false);
        clienteRepository.save(cliente);

        assertFalse(clienteRepository.findById(cliente.getId()).get().isActivo());
        assertTrue(clienteRepository.findByActivoTrue().stream().noneMatch(c -> c.getId().equals(cliente.getId())));
    }
}