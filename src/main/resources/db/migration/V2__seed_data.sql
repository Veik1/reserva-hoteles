-- Hoteles
INSERT INTO hotel (id, nombre, ciudad, direccion, activo) VALUES
  (1001, 'Seed Alvear', 'Seed Buenos Aires', 'Seed Av. Alvear 1891', true),
  (1002, 'Seed Llao Llao', 'Seed Bariloche', 'Seed Av. Bustillo Km 25', true),
  (1003, 'Seed Faena', 'Seed Buenos Aires', 'Seed Martha Salotti 445', true),
  (1004, 'Seed Hyatt', 'Seed Mendoza', 'Seed Chile 1124', true),
  (1005, 'Seed Delos', 'Seed Tigre', 'Seed Isla Delos', true);

-- Habitaciones
INSERT INTO habitacion (id, numero, tipo, disponible, precio, hotel_id, activo) VALUES
  (2001, 101, 'Seed Suite Deluxe', true, 5000, 1001, true),
  (2002, 102, 'Seed Doble Superior', true, 3500, 1001, true),
  (2003, 103, 'Seed Simple', true, 2000, 1001, true),
  (2004, 201, 'Seed Suite Lago', true, 6000, 1002, true),
  (2005, 202, 'Seed Doble Vista', true, 4000, 1002, true),
  (2006, 203, 'Seed Simple', true, 2200, 1002, true),
  (2007, 301, 'Seed Suite', true, 7000, 1003, true),
  (2008, 302, 'Seed Doble Premium', true, 4500, 1003, true),
  (2009, 303, 'Seed Simple', true, 2500, 1003, true),
  (2010, 401, 'Seed Suite Andes', true, 8000, 1004, true),
  (2011, 402, 'Seed Doble', true, 4700, 1004, true),
  (2012, 403, 'Seed Simple', true, 2600, 1004, true),
  (2013, 501, 'Seed Suite', true, 9000, 1005, true),
  (2014, 502, 'Seed Doble', true, 4800, 1005, true),
  (2015, 503, 'Seed Simple', true, 2700, 1005, true);

-- Clientes
INSERT INTO cliente (id, nombre, email, dni, activo) VALUES
  (3001, 'Seed Juan Perez', 'seed.juan@mail.com', '99990001', true),
  (3002, 'Seed Ana Gomez', 'seed.ana@mail.com', '99990002', true),
  (3003, 'Seed Carlos Ruiz', 'seed.carlos@mail.com', '99990003', true),
  (3004, 'Seed Lucia Diaz', 'seed.lucia@mail.com', '99990004', true),
  (3005, 'Seed Mariana Lopez', 'seed.mariana@mail.com', '99990005', true),
  (3006, 'Seed Federico Torres', 'seed.federico@mail.com', '99990006', true),
  (3007, 'Seed Sofía Romero', 'seed.sofia@mail.com', '99990007', true),
  (3008, 'Seed Martín Castro', 'seed.martin@mail.com', '99990008', true);

-- Reservas
INSERT INTO reserva (fecha_inicio, fecha_fin, cliente_id, habitacion_id, hotel_id, activo) VALUES
  ('2025-07-01', '2025-07-05', 3001, 2001, 1001, true),
  ('2025-07-03', '2025-07-07', 3002, 2002, 1001, true),
  ('2025-07-10', '2025-07-15', 3003, 2004, 1002, true),
  ('2025-07-12', '2025-07-14', 3004, 2005, 1002, true),
  ('2025-08-01', '2025-08-10', 3005, 2007, 1003, true),
  ('2025-08-05', '2025-08-08', 3006, 2008, 1003, true),
  ('2025-09-01', '2025-09-03', 3007, 2010, 1004, true),
  ('2025-09-02', '2025-09-06', 3008, 2011, 1004, true),
  ('2025-09-10', '2025-09-15', 3001, 2013, 1005, true),
  ('2025-09-12', '2025-09-14', 3002, 2014, 1005, true),
  ('2025-10-01', '2025-10-05', 3003, 2015, 1005, true),
  ('2025-10-10', '2025-10-15', 3004, 2003, 1001, true),
  ('2025-10-12', '2025-10-18', 3005, 2006, 1002, true),
  ('2025-11-01', '2025-11-07', 3006, 2009, 1003, true),
  ('2025-11-10', '2025-11-15', 3007, 2012, 1004, true),
  ('2025-12-01', '2025-12-10', 3008, 2015, 1005, true);

-- Usuarios
INSERT INTO usuario (id, username, password, role, activo) VALUES
  (1, 'admin', '$2a$10$i.NwHvQQGMjFfwnhexDKOezRNPBpGhD0cH5Fv6MoNwKd80Xse0emK', 'ADMIN', true),
  (2, 'usuario', '$2a$10$2Ecnem8zwCJKEYh5hkBES.sB7WXPJpYJR9d3.oXd2ra0BtyRihGr6', 'USER', true);

-- Refresh tokens (ejemplo, puedes dejar vacío si se generan en runtime)
-- INSERT INTO refresh_token (token, usuario_id, expiry_date) VALUES
--   ('token_de_ejemplo', 1, '2025-12-31 23:59:59');