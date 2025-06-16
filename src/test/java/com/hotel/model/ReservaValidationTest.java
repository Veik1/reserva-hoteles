package com.hotel.model;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ReservaValidationTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Reserva buildReserva(LocalDate inicio, LocalDate fin) {
        Reserva reserva = new Reserva();
        reserva.setFechaInicio(inicio);
        reserva.setFechaFin(fin);
        reserva.setActivo(true);

        // Mock de entidades requeridas (puedes reemplazar por mocks reales si usas
        // Mockito)
        Cliente cliente = new Cliente();
        cliente.setNombre("Test Cliente");
        cliente.setEmail("test@cliente.com");
        cliente.setDni("12345678");
        reserva.setCliente(cliente);

        Habitacion habitacion = new Habitacion();
        habitacion.setNumero(101);
        habitacion.setTipo("Suite");
        habitacion.setDisponible(true);
        habitacion.setPrecio(1000.0);
        Hotel hotel = new Hotel();
        hotel.setNombre("Test Hotel");
        hotel.setCiudad("Ciudad");
        hotel.setDireccion("Direccion");
        habitacion.setHotel(hotel);
        reserva.setHabitacion(habitacion);

        reserva.setHotel(hotel);

        return reserva;
    }

    @Test
    void reservaValida_noDebeTenerErrores() {
        Reserva reserva = buildReserva(LocalDate.of(2025, 6, 20), LocalDate.of(2025, 6, 22));
        Set<ConstraintViolation<Reserva>> violations = validator.validate(reserva);
        assertTrue(violations.isEmpty(), "No debe haber errores de validación para fechas válidas");
    }

    @Test
    void reservaInvalida_fechaInicioDespuesDeFechaFin_debeTenerError() {
        Reserva reserva = buildReserva(LocalDate.of(2025, 6, 25), LocalDate.of(2025, 6, 22));
        Set<ConstraintViolation<Reserva>> violations = validator.validate(reserva);
        assertFalse(violations.isEmpty(), "Debe haber errores de validación");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("anterior a la fecha de fin")));
    }

    @Test
    void reservaInvalida_fechaInicioIgualAFechaFin_debeTenerError() {
        Reserva reserva = buildReserva(LocalDate.of(2025, 6, 22), LocalDate.of(2025, 6, 22));
        Set<ConstraintViolation<Reserva>> violations = validator.validate(reserva);
        assertFalse(violations.isEmpty(), "Debe haber errores de validación");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("anterior a la fecha de fin")));
    }

    @Test
    void reservaInvalida_fechaInicioONull_noDebeFallarPorEstaValidacion() {
        Reserva reserva = buildReserva(null, LocalDate.of(2025, 6, 22));
        Set<ConstraintViolation<Reserva>> violations = validator.validate(reserva);
        // Solo debe fallar por @NotNull, no por la validación personalizada
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("fechaInicio")));
        // No debe fallar por la validación de fechas si falta una fecha
        assertFalse(violations.stream().anyMatch(v -> v.getMessage().contains("anterior a la fecha de fin")));
    }
}