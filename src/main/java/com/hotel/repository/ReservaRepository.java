package com.hotel.repository;

import com.hotel.model.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // No paginado
    List<Reserva> findByActivoTrue();
    List<Reserva> findByHotelIdAndActivoTrue(Long hotelId);

    // Paginado
    Page<Reserva> findByActivoTrue(Pageable pageable);
    Page<Reserva> findByHotelIdAndActivoTrue(Long hotelId, Pageable pageable);
}