package com.hotel.service;

import com.hotel.model.Cliente;
import com.hotel.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // No paginado
    public List<Cliente> obtenerTodos() {
        return clienteRepository.findByActivoTrue();
    }

    // Paginado
    public Page<Cliente> obtenerTodosPaginado(int page, int size) {
        return clienteRepository.findByActivoTrue(PageRequest.of(page, size));
    }

    public Optional<Cliente> obtenerPorId(Long id) {
        return clienteRepository.findById(id).filter(Cliente::isActivo);
    }

    public Cliente crearCliente(Cliente cliente) {
        cliente.setActivo(true);
        return clienteRepository.save(cliente);
    }

    public Cliente actualizarCliente(Long id, Cliente cliente) {
        return clienteRepository.findById(id)
                .filter(Cliente::isActivo)
                .map(c -> {
                    c.setNombre(cliente.getNombre());
                    c.setEmail(cliente.getEmail());
                    c.setDni(cliente.getDni());
                    return clienteRepository.save(c);
                }).orElse(null);
    }

    public boolean eliminarCliente(Long id) {
        return clienteRepository.findById(id)
                .filter(Cliente::isActivo)
                .map(c -> {
                    c.setActivo(false);
                    clienteRepository.save(c);
                    return true;
                }).orElse(false);
    }
}