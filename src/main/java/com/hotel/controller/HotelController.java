package com.hotel.controller;

import com.hotel.model.Hotel;
import com.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hoteles")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping
    public List<Hotel> listarHoteles() {
        return hotelService.obtenerTodos();
    }

    @GetMapping("/paginado")
    public Page<Hotel> listarHotelesPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return hotelService.obtenerTodosPaginado(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> obtenerHotel(@PathVariable Long id) {
        return hotelService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Hotel> crearHotel(@Valid @RequestBody Hotel hotel) {
        Hotel creado = hotelService.crearHotel(hotel);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> actualizarHotel(@PathVariable Long id, @Valid @RequestBody Hotel hotel) {
        Hotel actualizado = hotelService.actualizarHotel(id, hotel);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHotel(@PathVariable Long id) {
        if (hotelService.eliminarHotel(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Manejo de errores de validación para devolver 400 y detalles de los campos
    // inválidos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}