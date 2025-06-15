CREATE TABLE cliente (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255),
    email VARCHAR(255),
    dni VARCHAR(50)
);

CREATE TABLE habitacion (
    id BIGSERIAL PRIMARY KEY,
    numero INT,
    tipo VARCHAR(100),
    disponible BOOLEAN,
    precio DOUBLE PRECISION
);

CREATE TABLE reserva (
    id BIGSERIAL PRIMARY KEY,
    fecha_inicio DATE,
    fecha_fin DATE,
    cliente_id BIGINT REFERENCES cliente(id),
    habitacion_id BIGINT REFERENCES habitacion(id)
);