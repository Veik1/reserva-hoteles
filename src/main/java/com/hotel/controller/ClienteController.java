package com.hotel.controller;

import com.hotel.model.Cliente;
import com.hotel.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // No paginado
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.obtenerTodos();
    }

    // Paginado
    @GetMapping("/paginado")
    public Page<Cliente> listarClientesPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return clienteService.obtenerTodosPaginado(page, size);
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable Long id) {
        return clienteService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente clienteRequest) {
        Cliente guardado = clienteService.crearCliente(clienteRequest);
        return new ResponseEntity<>(guardado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteRequest) {
        Cliente actualizado = clienteService.actualizarCliente(id, clienteRequest);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        if (clienteService.eliminarCliente(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}