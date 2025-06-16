package com.hotel.controller;

import com.hotel.model.Habitacion;
import com.hotel.service.HabitacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/habitaciones")
@Tag(name = "Habitaciones", description = "Operaciones CRUD para habitaciones")
public class HabitacionController {

    @Autowired
    private HabitacionService habitacionService;

    @Operation(summary = "Obtener todas las habitaciones")
    @GetMapping
    public List<Habitacion> getAllHabitaciones() {
        return habitacionService.obtenerTodas();
    }

    @Operation(summary = "Obtener una habitación por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Habitacion> getHabitacionById(@PathVariable Long id) {
        Optional<Habitacion> habitacion = habitacionService.obtenerPorId(id);
        return habitacion.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva habitación")
    @PostMapping
    public Habitacion createHabitacion(@Valid @RequestBody Habitacion habitacion) {
        return habitacionService.crearHabitacion(habitacion);
    }

    @Operation(summary = "Actualizar una habitación existente")
    @PutMapping("/{id}")
    public ResponseEntity<Habitacion> updateHabitacion(@PathVariable Long id,
            @Valid @RequestBody Habitacion habitacion) {
        Habitacion updated = habitacionService.actualizarHabitacion(id, habitacion);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar una habitación")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabitacion(@PathVariable Long id) {
        return habitacionService.eliminarHabitacion(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}