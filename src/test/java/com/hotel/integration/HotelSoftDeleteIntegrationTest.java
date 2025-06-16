package com.hotel.integration;

import com.hotel.model.Hotel;
import com.hotel.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HotelSoftDeleteIntegrationTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void softDeleteHotel() {
        Hotel hotel = new Hotel();
        hotel.setNombre("SoftDelete Hotel " + UUID.randomUUID());
        hotel.setCiudad("Ciudad");
        hotel.setDireccion("Direccion");
        Hotel savedHotel = hotelRepository.save(hotel);

        savedHotel.setActivo(false);
        hotelRepository.save(savedHotel);

        assertFalse(hotelRepository.findById(savedHotel.getId()).get().isActivo());
        assertTrue(hotelRepository.findByActivoTrue().stream().noneMatch(h -> h.getId().equals(savedHotel.getId())));
    }
}