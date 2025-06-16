package com.hotel.service;

import com.hotel.model.Reserva;
import com.hotel.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testActualizarReservaExistente() {
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        reserva.setFechaInicio(LocalDate.now());
        reserva.setFechaFin(LocalDate.now().plusDays(1));
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        Reserva actualizada = reservaService.actualizarReserva(1L, reserva);
        assertNotNull(actualizada);
    }

    @Test
    void testActualizarReservaNoExistente() {
        Reserva reserva = new Reserva();
        reserva.setId(99L);
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        Reserva actualizada = reservaService.actualizarReserva(99L, reserva);
        assertNull(actualizada);
    }

    @Test
    void testEliminarReservaExistente() {
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        doNothing().when(reservaRepository).deleteById(1L);

        boolean eliminado = reservaService.eliminarReserva(1L);
        assertTrue(eliminado);
    }

    @Test
    void testEliminarReservaNoExistente() {
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        boolean eliminado = reservaService.eliminarReserva(99L);
        assertFalse(eliminado);
    }
}