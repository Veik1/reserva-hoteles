package com.hotel.integration;

import com.hotel.model.Cliente;
import com.hotel.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClienteIntegrationTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void crudCliente() {
        // CREATE
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");
        cliente.setEmail("juan@mail.com");
        cliente.setDni("12345678");
        Cliente guardado = clienteRepository.save(cliente);
        assertNotNull(guardado.getId());

        // READ
        Cliente encontrado = clienteRepository.findById(guardado.getId()).orElse(null);
        assertNotNull(encontrado);

        // UPDATE
        encontrado.setNombre("Juan Actualizado");
        Cliente actualizado = clienteRepository.save(encontrado);
        assertEquals("Juan Actualizado", actualizado.getNombre());

        // DELETE
        clienteRepository.deleteById(actualizado.getId());
        assertFalse(clienteRepository.findById(actualizado.getId()).isPresent());
    }
}