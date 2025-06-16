package com.hotel.controller;

import com.hotel.model.Habitacion;
import com.hotel.model.Hotel;
import com.hotel.service.HabitacionService;
import com.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
public class HabitacionController {
    @Autowired
    private HabitacionService habitacionService;
    @Autowired
    private HotelService hotelService;

    // No paginado
    @GetMapping
    public List<Habitacion> listarHabitaciones() {
        return habitacionService.obtenerTodas();
    }

    @GetMapping("/hotel/{hotelId}")
    public List<Habitacion> listarPorHotel(@PathVariable Long hotelId) {
        return habitacionService.obtenerPorHotel(hotelId);
    }

    // Paginado
    @GetMapping("/paginado")
    public Page<Habitacion> listarHabitacionesPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return habitacionService.obtenerTodasPaginado(page, size);
    }

    @GetMapping("/hotel/{hotelId}/paginado")
    public Page<Habitacion> listarPorHotelPaginado(
            @PathVariable Long hotelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return habitacionService.obtenerPorHotelPaginado(hotelId, page, size);
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<Habitacion> obtenerHabitacion(@PathVariable Long id) {
        return habitacionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crearHabitacion(@RequestBody Habitacion habitacion) {
        if (habitacion.getHotel() == null || habitacion.getHotel().getId() == null) {
            return ResponseEntity.badRequest().body("Debe especificar el hotel al que pertenece la habitación.");
        }
        Hotel hotel = hotelService.obtenerPorId(habitacion.getHotel().getId()).orElse(null);
        if (hotel == null) {
            return ResponseEntity.badRequest().body("Hotel no encontrado.");
        }
        habitacion.setHotel(hotel);
        Habitacion creada = habitacionService.crearHabitacion(habitacion);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarHabitacion(@PathVariable Long id, @RequestBody Habitacion habitacion) {
        if (habitacion.getHotel() == null || habitacion.getHotel().getId() == null) {
            return ResponseEntity.badRequest().body("Debe especificar el hotel al que pertenece la habitación.");
        }
        Hotel hotel = hotelService.obtenerPorId(habitacion.getHotel().getId()).orElse(null);
        if (hotel == null) {
            return ResponseEntity.badRequest().body("Hotel no encontrado.");
        }
        habitacion.setHotel(hotel);
        Habitacion actualizada = habitacionService.actualizarHabitacion(id, habitacion);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHabitacion(@PathVariable Long id) {
        if (habitacionService.eliminarHabitacion(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}