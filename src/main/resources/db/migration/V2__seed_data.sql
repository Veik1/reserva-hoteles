INSERT INTO cliente (nombre, email, dni) VALUES
('Juan Perez', 'juan@mail.com', '12345678'),
('Ana Gomez', 'ana@mail.com', '87654321');

INSERT INTO habitacion (numero, tipo, disponible, precio) VALUES
(101, 'Simple', true, 1000.0),
(102, 'Doble', true, 1800.0);

INSERT INTO reserva (fecha_inicio, fecha_fin, cliente_id, habitacion_id) VALUES
('2025-07-01', '2025-07-05', 1, 1),
('2025-07-10', '2025-07-15', 2, 2);