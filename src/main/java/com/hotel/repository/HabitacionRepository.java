package com.hotel.repository;

import com.hotel.model.Habitacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    // No paginado
    List<Habitacion> findByActivoTrue();
    List<Habitacion> findByHotelIdAndActivoTrue(Long hotelId);

    // Paginado
    Page<Habitacion> findByActivoTrue(Pageable pageable);
    Page<Habitacion> findByHotelIdAndActivoTrue(Long hotelId, Pageable pageable);
}