package com.hotel.integration;

import com.hotel.model.Cliente;
import com.hotel.model.Habitacion;
import com.hotel.model.Hotel;
import com.hotel.model.Reserva;
import com.hotel.repository.ClienteRepository;
import com.hotel.repository.HabitacionRepository;
import com.hotel.repository.HotelRepository;
import com.hotel.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservaHotelPaginationIntegrationTest {

    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private HabitacionRepository habitacionRepository;
    @Autowired
    private HotelRepository hotelRepository;

    private Long hotelId;

    @BeforeEach
    void setUp() {
        Hotel hotel = new Hotel();
        hotel.setNombre("Hotel Reserva " + UUID.randomUUID());
        hotel.setCiudad("Ciudad Reserva");
        hotel.setDireccion("Calle Reserva 456");
        hotel = hotelRepository.save(hotel);
        hotelId = hotel.getId();

        Cliente cliente = new Cliente();
        cliente.setNombre("Cliente Reserva " + UUID.randomUUID());
        cliente.setEmail("cliente" + UUID.randomUUID() + "@reserva.com");
        cliente.setDni("dni" + ThreadLocalRandom.current().nextInt(100000, 999999));
        cliente = clienteRepository.save(cliente);

        Habitacion habitacion = new Habitacion();
        habitacion.setNumero(ThreadLocalRandom.current().nextInt(100000, 999999));
        habitacion.setTipo("Suite");
        habitacion.setDisponible(true);
        habitacion.setPrecio(2000.0);
        habitacion.setHotel(hotel);
        habitacion = habitacionRepository.save(habitacion);

        for (int i = 0; i < 5; i++) {
            Reserva reserva = new Reserva();
            reserva.setCliente(cliente);
            reserva.setHabitacion(habitacion);
            reserva.setHotel(hotel);
            reserva.setFechaInicio(LocalDate.now().plusDays(i));
            reserva.setFechaFin(LocalDate.now().plusDays(i + 2));
            reservaRepository.save(reserva);
        }
    }

    @Test
    void testPaginacionReservasPorHotel() {
        Page<Reserva> page0 = reservaRepository.findByHotelIdAndActivoTrue(hotelId,
                org.springframework.data.domain.PageRequest.of(0, 2));
        assertEquals(2, page0.getContent().size());
        assertEquals(5, page0.getTotalElements());

        Page<Reserva> page1 = reservaRepository.findByHotelIdAndActivoTrue(hotelId,
                org.springframework.data.domain.PageRequest.of(1, 2));
        assertEquals(2, page1.getContent().size());

        Page<Reserva> page2 = reservaRepository.findByHotelIdAndActivoTrue(hotelId,
                org.springframework.data.domain.PageRequest.of(2, 2));
        assertEquals(1, page2.getContent().size());
    }
}