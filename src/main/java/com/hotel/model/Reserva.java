package com.hotel.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Schema(description = "Reserva de una habitación por un cliente")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la reserva", example = "1")
    private Long id;

    @NotNull
    @ManyToOne
    @Schema(description = "Cliente que realiza la reserva")
    private Cliente cliente;

    @NotNull
    @ManyToOne
    @Schema(description = "Habitación reservada")
    private Habitacion habitacion;

    @NotNull
    @Schema(description = "Fecha de inicio de la reserva", example = "2024-07-01")
    private LocalDate fechaInicio;

    @NotNull
    @Schema(description = "Fecha de fin de la reserva", example = "2024-07-05")
    private LocalDate fechaFin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}