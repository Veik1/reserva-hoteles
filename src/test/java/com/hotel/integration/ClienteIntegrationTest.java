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
class ClienteIntegrationTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void crudCliente() {
        // CREATE
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan " + UUID.randomUUID());
        cliente.setEmail("juan" + UUID.randomUUID() + "@mail.com");
        cliente.setDni("dni" + ThreadLocalRandom.current().nextInt(100000, 999999));
        Cliente guardado = clienteRepository.save(cliente);
        assertNotNull(guardado.getId());

        // READ
        Cliente encontrado = clienteRepository.findById(guardado.getId()).orElse(null);
        assertNotNull(encontrado);

        // UPDATE
        encontrado.setNombre("Juan Actualizado " + UUID.randomUUID());
        Cliente actualizado = clienteRepository.save(encontrado);
        assertTrue(actualizado.getNombre().startsWith("Juan Actualizado"));

        // DELETE
        clienteRepository.deleteById(actualizado.getId());
        assertFalse(clienteRepository.findById(actualizado.getId()).isPresent());
    }
}