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

    public Reserva actualizarReserva(Long id, Reserva reserva) {
        return reservaRepository.findById(id)
                .map(r -> {
                    r.setFechaInicio(reserva.getFechaInicio());
                    r.setFechaFin(reserva.getFechaFin());
                    r.setCliente(reserva.getCliente());
                    r.setHabitacion(reserva.getHabitacion());
                    return reservaRepository.save(r);
                })
                .orElse(null);
    }

    public boolean eliminarReserva(Long id) {
        if (reservaRepository.findById(id).isPresent()) {
            reservaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}