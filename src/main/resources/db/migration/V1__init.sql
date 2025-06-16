CREATE TABLE hotel (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE,
    ciudad VARCHAR(100) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE cliente (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    dni VARCHAR(50) NOT NULL UNIQUE,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE habitacion (
    id BIGSERIAL PRIMARY KEY,
    numero INT NOT NULL,
    tipo VARCHAR(100) NOT NULL,
    disponible BOOLEAN NOT NULL,
    precio DOUBLE PRECISION NOT NULL,
    hotel_id BIGINT NOT NULL REFERENCES hotel(id),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    UNIQUE (numero, hotel_id)
);

CREATE TABLE reserva (
    id BIGSERIAL PRIMARY KEY,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    cliente_id BIGINT NOT NULL REFERENCES cliente(id),
    habitacion_id BIGINT NOT NULL REFERENCES habitacion(id),
    hotel_id BIGINT NOT NULL REFERENCES hotel(id),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE refresh_token (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    usuario_id BIGINT NOT NULL REFERENCES usuario(id),
    expiry_date TIMESTAMP NOT NULL
);