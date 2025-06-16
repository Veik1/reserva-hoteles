package com.hotel.service;

import com.hotel.model.Habitacion;
import com.hotel.repository.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    public Habitacion crearHabitacion(Habitacion habitacion) {
        return habitacionRepository.save(habitacion);
    }

    public List<Habitacion> obtenerTodas() {
        return habitacionRepository.findAll();
    }

    public Optional<Habitacion> obtenerPorId(Long id) {
        return habitacionRepository.findById(id);
    }

    public Habitacion actualizarHabitacion(Long id, Habitacion habitacion) {
        return habitacionRepository.findById(id)
                .map(h -> {
                    h.setNumero(habitacion.getNumero());
                    h.setTipo(habitacion.getTipo());
                    h.setDisponible(habitacion.isDisponible());
                    h.setPrecio(habitacion.getPrecio());
                    return habitacionRepository.save(h);
                })
                .orElse(null);
    }

    public boolean eliminarHabitacion(Long id) {
        if (habitacionRepository.findById(id).isPresent()) {
            habitacionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}