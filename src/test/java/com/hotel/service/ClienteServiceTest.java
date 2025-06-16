package com.hotel.service;

import com.hotel.model.Cliente;
import com.hotel.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testActualizarClienteExistente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Nuevo Nombre");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente actualizado = clienteService.actualizarCliente(1L, cliente);
        assertNotNull(actualizado);
    }

    @Test
    void testActualizarClienteNoExistente() {
        Cliente cliente = new Cliente();
        cliente.setId(99L);
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        Cliente actualizado = clienteService.actualizarCliente(99L, cliente);
        assertNull(actualizado);
    }

    @Test
    void testEliminarClienteExistente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteRepository).deleteById(1L);

        boolean eliminado = clienteService.eliminarCliente(1L);
        assertTrue(eliminado);
    }

    @Test
    void testEliminarClienteNoExistente() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        boolean eliminado = clienteService.eliminarCliente(99L);
        assertFalse(eliminado);
    }
}