package com.hotel.integration;

import com.hotel.model.Habitacion;
import com.hotel.model.Hotel;
import com.hotel.repository.HabitacionRepository;
import com.hotel.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HabitacionHotelPaginationIntegrationTest {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private HotelRepository hotelRepository;

    private Long hotelId;

    @BeforeEach
    void setUp() {
        Hotel hotel = new Hotel();
        hotel.setNombre("Hotel Test " + UUID.randomUUID());
        hotel.setCiudad("Ciudad Test");
        hotel.setDireccion("Calle Falsa 123");
        hotel = hotelRepository.save(hotel);
        hotelId = hotel.getId();

        for (int i = 1; i <= 5; i++) {
            Habitacion h = new Habitacion();
            h.setNumero(ThreadLocalRandom.current().nextInt(100000, 999999));
            h.setTipo("Tipo " + i);
            h.setDisponible(true);
            h.setPrecio(1000.0 + i * 100);
            h.setHotel(hotel);
            habitacionRepository.save(h);
        }
    }

    @Test
    void testPaginacionHabitacionesPorHotel() {
        Page<Habitacion> page0 = habitacionRepository.findByHotelIdAndActivoTrue(hotelId,
                org.springframework.data.domain.PageRequest.of(0, 2));
        assertEquals(2, page0.getContent().size());
        assertEquals(5, page0.getTotalElements());

        Page<Habitacion> page1 = habitacionRepository.findByHotelIdAndActivoTrue(hotelId,
                org.springframework.data.domain.PageRequest.of(1, 2));
        assertEquals(2, page1.getContent().size());

        Page<Habitacion> page2 = habitacionRepository.findByHotelIdAndActivoTrue(hotelId,
                org.springframework.data.domain.PageRequest.of(2, 2));
        assertEquals(1, page2.getContent().size());
    }
}