package com.hotel.integration;

import com.hotel.model.Cliente;
import com.hotel.model.Habitacion;
import com.hotel.model.Reserva;
import com.hotel.repository.ClienteRepository;
import com.hotel.repository.HabitacionRepository;
import com.hotel.repository.ReservaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservaIntegrationTest {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Test
    void crudReserva() {
        // Crear cliente y habitación
        Cliente cliente = new Cliente();
        cliente.setNombre("Test Cliente");
        cliente.setEmail("testcliente@mail.com");
        cliente.setDni("99999999");
        cliente = clienteRepository.save(cliente);

        Habitacion habitacion = new Habitacion();
        habitacion.setNumero(200);
        habitacion.setTipo("Suite");
        habitacion.setDisponible(true);
        habitacion.setPrecio(2500.0);
        habitacion = habitacionRepository.save(habitacion);

        // CREATE
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setHabitacion(habitacion);
        reserva.setFechaInicio(LocalDate.now().plusDays(1));
        reserva.setFechaFin(LocalDate.now().plusDays(3));
        Reserva guardada = reservaRepository.save(reserva);
        assertNotNull(guardada.getId());

        // READ
        Reserva encontrada = reservaRepository.findById(guardada.getId()).orElse(null);
        assertNotNull(encontrada);

        // UPDATE
        encontrada.setFechaFin(LocalDate.now().plusDays(5));
        Reserva actualizada = reservaRepository.save(encontrada);
        assertEquals(LocalDate.now().plusDays(5), actualizada.getFechaFin());

        // DELETE
        reservaRepository.deleteById(actualizada.getId());
        assertFalse(reservaRepository.findById(actualizada.getId()).isPresent());
    }
}