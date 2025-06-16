package com.hotel.service;

import com.hotel.model.Hotel;
import com.hotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    // No paginado
    public List<Hotel> obtenerTodos() {
        return hotelRepository.findByActivoTrue();
    }

    // Paginado
    public Page<Hotel> obtenerTodosPaginado(int page, int size) {
        return hotelRepository.findByActivoTrue(PageRequest.of(page, size));
    }

    public Optional<Hotel> obtenerPorId(Long id) {
        return hotelRepository.findById(id).filter(Hotel::isActivo);
    }

    public Hotel crearHotel(Hotel hotel) {
        hotel.setActivo(true);
        return hotelRepository.save(hotel);
    }

    public Hotel actualizarHotel(Long id, Hotel hotel) {
        return hotelRepository.findById(id)
                .filter(Hotel::isActivo)
                .map(h -> {
                    h.setNombre(hotel.getNombre());
                    h.setCiudad(hotel.getCiudad());
                    h.setDireccion(hotel.getDireccion());
                    return hotelRepository.save(h);
                }).orElse(null);
    }

    public boolean eliminarHotel(Long id) {
        return hotelRepository.findById(id)
                .filter(Hotel::isActivo)
                .map(h -> {
                    h.setActivo(false);
                    hotelRepository.save(h);
                    return true;
                }).orElse(false);
    }
}