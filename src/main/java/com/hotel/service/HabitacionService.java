package com.hotel.service;

import com.hotel.model.Habitacion;
import com.hotel.repository.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    // No paginado
    public List<Habitacion> obtenerTodas() {
        return habitacionRepository.findByActivoTrue();
    }

    public List<Habitacion> obtenerPorHotel(Long hotelId) {
        return habitacionRepository.findByHotelIdAndActivoTrue(hotelId);
    }

    // Paginado
    public Page<Habitacion> obtenerTodasPaginado(int page, int size) {
        return habitacionRepository.findByActivoTrue(PageRequest.of(page, size));
    }

    public Page<Habitacion> obtenerPorHotelPaginado(Long hotelId, int page, int size) {
        return habitacionRepository.findByHotelIdAndActivoTrue(hotelId, PageRequest.of(page, size));
    }

    public Optional<Habitacion> obtenerPorId(Long id) {
        return habitacionRepository.findById(id).filter(Habitacion::isActivo);
    }

    public Habitacion crearHabitacion(Habitacion habitacion) {
        habitacion.setActivo(true);
        return habitacionRepository.save(habitacion);
    }

    public Habitacion actualizarHabitacion(Long id, Habitacion habitacion) {
        return habitacionRepository.findById(id)
                .filter(Habitacion::isActivo)
                .map(h -> {
                    h.setNumero(habitacion.getNumero());
                    h.setTipo(habitacion.getTipo());
                    h.setDisponible(habitacion.getDisponible());
                    h.setPrecio(habitacion.getPrecio());
                    h.setHotel(habitacion.getHotel());
                    return habitacionRepository.save(h);
                })
                .orElse(null);
    }

    public boolean eliminarHabitacion(Long id) {
        return habitacionRepository.findById(id)
                .filter(Habitacion::isActivo)
                .map(h -> {
                    h.setActivo(false);
                    habitacionRepository.save(h);
                    return true;
                }).orElse(false);
    }
}