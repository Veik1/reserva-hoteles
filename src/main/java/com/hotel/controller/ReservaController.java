package com.hotel.controller;

import com.hotel.model.Reserva;
import com.hotel.model.Cliente;
import com.hotel.model.Habitacion;
import com.hotel.model.Hotel;
import com.hotel.service.ReservaService;
import com.hotel.service.ClienteService;
import com.hotel.service.HabitacionService;
import com.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private HabitacionService habitacionService;
    @Autowired
    private HotelService hotelService;

    // No paginado
    @GetMapping
    public List<Reserva> listarReservas() {
        return reservaService.obtenerTodas();
    }

    @GetMapping("/hotel/{hotelId}")
    public List<Reserva> listarPorHotel(@PathVariable Long hotelId) {
        return reservaService.obtenerPorHotel(hotelId);
    }

    // Paginado
    @GetMapping("/paginado")
    public Page<Reserva> listarReservasPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return reservaService.obtenerTodasPaginado(page, size);
    }

    @GetMapping("/hotel/{hotelId}/paginado")
    public Page<Reserva> listarPorHotelPaginado(
            @PathVariable Long hotelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return reservaService.obtenerPorHotelPaginado(hotelId, page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReserva(@PathVariable Long id) {
        return reservaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crearReserva(@RequestBody Reserva reservaRequest) {
        if (reservaRequest.getCliente() == null || reservaRequest.getCliente().getId() == null ||
                reservaRequest.getHabitacion() == null || reservaRequest.getHabitacion().getId() == null ||
                reservaRequest.getHotel() == null || reservaRequest.getHotel().getId() == null) {
            return ResponseEntity.badRequest().body("Debe especificar cliente, habitación y hotel.");
        }
        Cliente cliente = clienteService.obtenerPorId(reservaRequest.getCliente().getId()).orElse(null);
        Habitacion habitacion = habitacionService.obtenerPorId(reservaRequest.getHabitacion().getId()).orElse(null);
        Hotel hotel = hotelService.obtenerPorId(reservaRequest.getHotel().getId()).orElse(null);

        if (cliente == null || habitacion == null || hotel == null) {
            return ResponseEntity.badRequest().body("Cliente, habitación o hotel no encontrados.");
        }
        reservaRequest.setCliente(cliente);
        reservaRequest.setHabitacion(habitacion);
        reservaRequest.setHotel(hotel);

        Reserva guardada = reservaService.crearReserva(reservaRequest);
        return new ResponseEntity<>(guardada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarReserva(@PathVariable Long id, @RequestBody Reserva reservaRequest) {
        if (reservaRequest.getCliente() == null || reservaRequest.getCliente().getId() == null ||
                reservaRequest.getHabitacion() == null || reservaRequest.getHabitacion().getId() == null ||
                reservaRequest.getHotel() == null || reservaRequest.getHotel().getId() == null) {
            return ResponseEntity.badRequest().body("Debe especificar cliente, habitación y hotel.");
        }
        Cliente cliente = clienteService.obtenerPorId(reservaRequest.getCliente().getId()).orElse(null);
        Habitacion habitacion = habitacionService.obtenerPorId(reservaRequest.getHabitacion().getId()).orElse(null);
        Hotel hotel = hotelService.obtenerPorId(reservaRequest.getHotel().getId()).orElse(null);

        if (cliente == null || habitacion == null || hotel == null) {
            return ResponseEntity.badRequest().body("Cliente, habitación o hotel no encontrados.");
        }
        reservaRequest.setCliente(cliente);
        reservaRequest.setHabitacion(habitacion);
        reservaRequest.setHotel(hotel);

        Reserva actualizada = reservaService.actualizarReserva(id, reservaRequest);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        if (reservaService.eliminarReserva(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}