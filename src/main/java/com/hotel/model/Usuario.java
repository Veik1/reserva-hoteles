package com.hotel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username; // Usar el email del cliente como username

    @NotBlank
    private String password;

    private Boolean activo = true;
    private Boolean enabled = true;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @NotBlank
    private String role; // "ADMIN" o "USER"

    public Usuario() {
    }

    public Usuario(String username, String password, String role, Boolean activo, Boolean enabled, Cliente cliente) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.activo = activo;
        this.enabled = enabled;
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}