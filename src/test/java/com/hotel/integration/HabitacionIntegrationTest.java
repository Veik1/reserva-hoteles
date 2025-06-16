package com.hotel.integration;

import com.hotel.model.Habitacion;
import com.hotel.repository.HabitacionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HabitacionIntegrationTest {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Test
    void crudHabitacion() {
        // CREATE
        Habitacion habitacion = new Habitacion();
        habitacion.setNumero(101);
        habitacion.setTipo("Suite");
        habitacion.setDisponible(true);
        habitacion.setPrecio(2000.0);
        Habitacion guardada = habitacionRepository.save(habitacion);
        assertNotNull(guardada.getId());

        // READ
        Habitacion encontrada = habitacionRepository.findById(guardada.getId()).orElse(null);
        assertNotNull(encontrada);

        // UPDATE
        encontrada.setTipo("Doble");
        Habitacion actualizada = habitacionRepository.save(encontrada);
        assertEquals("Doble", actualizada.getTipo());

        // DELETE
        habitacionRepository.deleteById(actualizada.getId());
        assertFalse(habitacionRepository.findById(actualizada.getId()).isPresent());
    }
}