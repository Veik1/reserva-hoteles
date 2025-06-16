package com.hotel.service;

import com.hotel.model.Reserva;
import com.hotel.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    // No paginado
    public List<Reserva> obtenerTodas() {
        return reservaRepository.findByActivoTrue();
    }

    public List<Reserva> obtenerPorHotel(Long hotelId) {
        return reservaRepository.findByHotelIdAndActivoTrue(hotelId);
    }

    // Paginado
    public Page<Reserva> obtenerTodasPaginado(int page, int size) {
        return reservaRepository.findByActivoTrue(PageRequest.of(page, size));
    }

    public Page<Reserva> obtenerPorHotelPaginado(Long hotelId, int page, int size) {
        return reservaRepository.findByHotelIdAndActivoTrue(hotelId, PageRequest.of(page, size));
    }

    public Optional<Reserva> obtenerPorId(Long id) {
        return reservaRepository.findById(id).filter(Reserva::isActivo);
    }

    public Reserva crearReserva(Reserva reserva) {
        reserva.setActivo(true);
        return reservaRepository.save(reserva);
    }

    public Reserva actualizarReserva(Long id, Reserva reserva) {
        return reservaRepository.findById(id)
                .filter(Reserva::isActivo)
                .map(r -> {
                    r.setFechaInicio(reserva.getFechaInicio());
                    r.setFechaFin(reserva.getFechaFin());
                    r.setCliente(reserva.getCliente());
                    r.setHabitacion(reserva.getHabitacion());
                    r.setHotel(reserva.getHotel());
                    return reservaRepository.save(r);
                }).orElse(null);
    }

    public boolean eliminarReserva(Long id) {
        return reservaRepository.findById(id)
                .filter(Reserva::isActivo)
                .map(r -> {
                    r.setActivo(false);
                    reservaRepository.save(r);
                    return true;
                }).orElse(false);
    }
}