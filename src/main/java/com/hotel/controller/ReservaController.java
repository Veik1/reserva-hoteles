package com.hotel.controller;

import com.hotel.model.Reserva;
import com.hotel.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservas")
@Tag(name = "Reservas", description = "Operaciones CRUD para reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Operation(summary = "Obtener todas las reservas")
    @GetMapping
    public List<Reserva> getAllReservas() {
        return reservaService.obtenerTodas();
    }

    @Operation(summary = "Obtener una reserva por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getReservaById(@PathVariable Long id) {
        Optional<Reserva> reserva = reservaService.obtenerPorId(id);
        return reserva.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva reserva")
    @PostMapping
    public Reserva createReserva(@Valid @RequestBody Reserva reserva) {
        return reservaService.crearReserva(reserva);
    }

    @Operation(summary = "Actualizar una reserva existente")
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> updateReserva(@PathVariable Long id, @Valid @RequestBody Reserva reserva) {
        Reserva updated = reservaService.actualizarReserva(id, reserva);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar una reserva")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        return reservaService.eliminarReserva(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}