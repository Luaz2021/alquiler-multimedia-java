USE `library_db`;

-- ========================================================
-- 0. VACIAR TABLAS EN ORDEN SEGURO (DE HIJO A PADRE)
-- ========================================================
DELETE FROM `prestamos`;
DELETE FROM `articulos`;
DELETE FROM `usuarios`;

-- Reiniciar el autoincremental de préstamos a 1
ALTER TABLE `prestamos` AUTO_INCREMENT = 1;

-- ========================================================
-- 1. INSERTAR USUARIOS (ALUMNOS Y DOCENTES)
-- ========================================================
INSERT INTO `usuarios` (`dni`, `nombre`, `edad`, `asignatura`, `tipo`) VALUES
-- Alumnos
('Z2131362A', 'Usuario Principal', 25, NULL, 'ALUMNO'),
('12345678A', 'Carlos Mendoza', 20, NULL, 'ALUMNO'),
('87654321B', 'Lucía Fernández', 22, NULL, 'ALUMNO'),
('X5566778K', 'Andrés Morales', 23, NULL, 'ALUMNO'),
('Y9876543C', 'Elena Rostova', 24, NULL, 'ALUMNO'),

-- Docentes
('45678912D', 'Prof. Alberto Gómez', 45, 'Programación Orientada a Objetos', 'DOCENTE'),
('78912345E', 'Dra. María Valverde', 50, 'Bases de Datos Relacionales', 'DOCENTE'),
('X1122334F', 'Dr. John Miller', 39, 'Sistemas Distribuidos', 'DOCENTE');

-- ========================================================
-- 2. INSERTAR ARTÍCULOS (PELÍCULAS Y VIDEOJUEGOS)
-- ========================================================
INSERT INTO `articulos` (`num_serie`, `titulo`, `genero`, `plataforma`, `tipo`) VALUES
-- Películas
('PEL-001', 'Interstellar', 'Ciencia Ficción', NULL, 'PELICULA'),
('PEL-002', 'El Padrino', 'Drama/Crimen', NULL, 'PELICULA'),
('PEL-003', 'El Viaje de Chihiro', 'Animación/Fantasía', NULL, 'PELICULA'),
('PEL-004', 'Inception', 'Ciencia Ficción', NULL, 'PELICULA'),

-- Videojuegos
('VJ-101', 'The Legend of Zelda: Tears of the Kingdom', 'Aventura', 'Nintendo Switch', 'VIDEOJUEGO'),
('VJ-102', 'Elden Ring', 'Rol/Acción', 'PC', 'VIDEOJUEGO'),
('VJ-103', 'God of War Ragnarök', 'Acción/Aventura', 'PS5', 'VIDEOJUEGO'),
('VJ-104', 'Halo Infinite', 'Shooter', 'Xbox Series X', 'VIDEOJUEGO'),
('VJ-105', 'Cyberpunk 2077', 'Rol/Mundo Abierto', 'PC', 'VIDEOJUEGO');

-- ========================================================
-- 3. INSERTAR PRÉSTAMOS
-- ========================================================
INSERT INTO `prestamos` (`usuario_dni`, `articulo_num_serie`, `fecha_prestamo`, `fecha_devolucion_esperada`, `fecha_devolucion_real`) VALUES
-- Devuelto a tiempo
('Z2131362A', 'VJ-102', DATE_SUB(CURDATE(), INTERVAL 10 DAY), DATE_SUB(CURDATE(), INTERVAL 2 DAY), DATE_SUB(CURDATE(), INTERVAL 3 DAY)),

-- Devuelto con retraso
('12345678A', 'PEL-001', DATE_SUB(CURDATE(), INTERVAL 20 DAY), DATE_SUB(CURDATE(), INTERVAL 10 DAY), DATE_SUB(CURDATE(), INTERVAL 2 DAY)),

-- Activo en curso
('87654321B', 'VJ-101', DATE_SUB(CURDATE(), INTERVAL 2 DAY), DATE_ADD(CURDATE(), INTERVAL 5 DAY), NULL),

-- Activo en curso
('45678912D', 'PEL-003', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), NULL),

-- Vencido / Retrasado
('Y9876543C', 'VJ-103', DATE_SUB(CURDATE(), INTERVAL 15 DAY), DATE_SUB(CURDATE(), INTERVAL 5 DAY), NULL),

-- Vencido / Retrasado
('78912345E', 'PEL-002', DATE_SUB(CURDATE(), INTERVAL 30 DAY), DATE_SUB(CURDATE(), INTERVAL 15 DAY), NULL);