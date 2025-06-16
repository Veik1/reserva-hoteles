package com.hotel.integration;

import com.hotel.model.Hotel;
import com.hotel.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HotelPaginationIntegrationTest {

    @Autowired
    private HotelRepository hotelRepository;

    @BeforeEach
    void setUp() {
        for (int i = 1; i <= 5; i++) {
            Hotel hotel = new Hotel();
            hotel.setNombre("Hotel " + i + " " + UUID.randomUUID());
            hotel.setCiudad("Ciudad " + i);
            hotel.setDireccion("Direccion " + i);
            hotelRepository.save(hotel);
        }
    }

    @Test
    void testPaginacionHoteles() {
        Page<Hotel> page0 = hotelRepository.findByActivoTrue(PageRequest.of(0, 2));
        assertEquals(2, page0.getContent().size());
        assertTrue(page0.getTotalElements() >= 5);

        Page<Hotel> page1 = hotelRepository.findByActivoTrue(PageRequest.of(1, 2));
        assertEquals(2, page1.getContent().size());
    }
}