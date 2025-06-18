package com.hotel.repository;

import com.hotel.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // No paginado
    List<Cliente> findByActivoTrue();

    // Paginado
    Page<Cliente> findByActivoTrue(Pageable pageable);

    Cliente findByEmail(String email);

    Cliente findByDni(String dni);
}