package com.hotel.service;

import com.hotel.model.Habitacion;
import com.hotel.repository.HabitacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HabitacionServiceTest {

    @Mock
    private HabitacionRepository habitacionRepository;

    @InjectMocks
    private HabitacionService habitacionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testActualizarHabitacionExistente() {
        Habitacion habitacion = new Habitacion();
        habitacion.setId(1L);
        habitacion.setNumero(101);
        when(habitacionRepository.findById(1L)).thenReturn(Optional.of(habitacion));
        when(habitacionRepository.save(any(Habitacion.class))).thenReturn(habitacion);

        Habitacion actualizada = habitacionService.actualizarHabitacion(1L, habitacion);
        assertNotNull(actualizada);
    }

    @Test
    void testActualizarHabitacionNoExistente() {
        Habitacion habitacion = new Habitacion();
        habitacion.setId(99L);
        when(habitacionRepository.findById(99L)).thenReturn(Optional.empty());

        Habitacion actualizada = habitacionService.actualizarHabitacion(99L, habitacion);
        assertNull(actualizada);
    }

    @Test
    void testEliminarHabitacionExistente() {
        Habitacion habitacion = new Habitacion();
        habitacion.setId(1L);
        when(habitacionRepository.findById(1L)).thenReturn(Optional.of(habitacion));
        doNothing().when(habitacionRepository).deleteById(1L);

        boolean eliminado = habitacionService.eliminarHabitacion(1L);
        assertTrue(eliminado);
    }

    @Test
    void testEliminarHabitacionNoExistente() {
        when(habitacionRepository.findById(99L)).thenReturn(Optional.empty());

        boolean eliminado = habitacionService.eliminarHabitacion(99L);
        assertFalse(eliminado);
    }
}