package com.hotel.integration;

import com.hotel.model.Habitacion;
import com.hotel.model.Hotel;
import com.hotel.repository.HabitacionRepository;
import com.hotel.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class HabitacionIntegrationTest {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void crearHabitacionConDatosUnicosNoChocaConSeeds() {
        // Crear hotel con nombre único
        Hotel hotel = new Hotel();
        hotel.setNombre("Hotel Test " + UUID.randomUUID());
        hotel.setCiudad("Ciudad Test");
        hotel.setDireccion("Dirección Test");
        hotel.setActivo(true);
        Hotel hotelGuardado = hotelRepository.save(hotel);

        // Crear habitación con número único
        Habitacion habitacion = new Habitacion();
        habitacion.setNumero(ThreadLocalRandom.current().nextInt(100000, 999999));
        habitacion.setTipo("Test Tipo");
        habitacion.setDisponible(true);
        habitacion.setPrecio(1234.56);
        habitacion.setHotel(hotelGuardado);
        habitacion.setActivo(true);

        Habitacion guardada = habitacionRepository.save(habitacion);

        assertThat(guardada.getId()).isNotNull();
        assertThat(guardada.getNumero()).isEqualTo(habitacion.getNumero());
        assertThat(guardada.getHotel().getId()).isEqualTo(hotelGuardado.getId());
    }
}