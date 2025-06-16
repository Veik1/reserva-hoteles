package com.hotel.validation;

import com.hotel.model.Reserva;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FechaReservaValidaValidator implements ConstraintValidator<FechaReservaValida, Reserva> {
    @Override
    public boolean isValid(Reserva reserva, ConstraintValidatorContext context) {
        if (reserva == null) return true;
        if (reserva.getFechaInicio() == null || reserva.getFechaFin() == null) {
            return true; // Otra validación se encarga de nulls
        }
        return reserva.getFechaInicio().isBefore(reserva.getFechaFin());
    }
}