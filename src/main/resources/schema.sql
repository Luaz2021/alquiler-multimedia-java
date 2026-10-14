-- Elimina la base de datos si ya existe para empezar limpio
DROP DATABASE IF EXISTS `library_db`;
CREATE DATABASE `library_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `library_db`;

-- --------------------------------------------------------
-- Tabla: usuarios (Herencia: Alumno y Docente)
-- --------------------------------------------------------
CREATE TABLE `usuarios` (
  `dni` VARCHAR(20) NOT NULL,
  `nombre` VARCHAR(100) NOT NULL,
  `edad` INT NOT NULL,
  `asignatura` VARCHAR(100) DEFAULT NULL, -- Exclusivo de DOCENTE (NULL en ALUMNO)
  `tipo` VARCHAR(20) NOT NULL,            -- 'ALUMNO' o 'DOCENTE'
  PRIMARY KEY (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
-- Tabla: articulos (Herencia: Videojuego y Pelicula)
-- --------------------------------------------------------
CREATE TABLE `articulos` (
  `num_serie` VARCHAR(50) NOT NULL,
  `titulo` VARCHAR(150) NOT NULL,         -- Título/Nombre del artículo
  `genero` VARCHAR(50) NOT NULL,
  `plataforma` VARCHAR(50) DEFAULT NULL,  -- Exclusivo de VIDEOJUEGO (NULL en PELICULA)
  `tipo` VARCHAR(20) NOT NULL,            -- 'VIDEOJUEGO' o 'PELICULA'
  PRIMARY KEY (`num_serie`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
-- Tabla: prestamos
-- --------------------------------------------------------
CREATE TABLE `prestamos` (
  `id_prestamo` INT AUTO_INCREMENT NOT NULL,
  `usuario_dni` VARCHAR(20) NOT NULL,
  `articulo_num_serie` VARCHAR(50) NOT NULL,
  `fecha_prestamo` DATE NOT NULL,
  `fecha_devolucion_esperada` DATE NOT NULL,
  `fecha_devolucion_real` DATE DEFAULT NULL, -- NULL mientras esté en préstamo
  PRIMARY KEY (`id_prestamo`),
  CONSTRAINT `fk_prestamo_usuario` 
    FOREIGN KEY (`usuario_dni`) 
    REFERENCES `usuarios` (`dni`) 
    ON UPDATE CASCADE 
    ON DELETE RESTRICT,
  CONSTRAINT `fk_prestamo_articulo` 
    FOREIGN KEY (`articulo_num_serie`) 
    REFERENCES `articulos` (`num_serie`) 
    ON UPDATE CASCADE 
    ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;