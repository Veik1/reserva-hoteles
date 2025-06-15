package com.hotel.service;

import com.hotel.model.Reserva;
import com.hotel.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public Reserva crearReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public List<Reserva> obtenerTodas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> obtenerPorId(Long id) {
        return reservaRepository.findById(id);
    }

    public boolean cancelarReserva(Long id) {
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Reserva actualizarReserva(Long id, Reserva reservaActualizada) {
        return reservaRepository.findById(id).map(reserva -> {
            reserva.setCliente(reservaActualizada.getCliente());
            reserva.setHabitacion(reservaActualizada.getHabitacion());
            reserva.setFechaInicio(reservaActualizada.getFechaInicio());
            reserva.setFechaFin(reservaActualizada.getFechaFin());
            return reservaRepository.save(reserva);
        }).orElse(null);
    }
}