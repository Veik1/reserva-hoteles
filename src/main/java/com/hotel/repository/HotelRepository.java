package com.hotel.repository;

import com.hotel.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    // No paginado
    List<Hotel> findByActivoTrue();

    // Paginado
    Page<Hotel> findByActivoTrue(Pageable pageable);
}