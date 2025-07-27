# PROYECTO-INTERFACES-GESTION-ASISTENCIA

CREATE DATABASE IF NOT EXISTS BASEDATO;
USE BASEDATO;

DROP TABLE IF EXISTS asistencias;
DROP TABLE IF EXISTS alumnos;
DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (
                          id VARCHAR(50) PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          contrasena VARCHAR(100) NOT NULL,
                          rol ENUM('ADMIN', 'DOCENTE') NOT NULL
);

CREATE TABLE alumnos (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL
);

CREATE TABLE asistencias (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             alumno_id INT,
                             fecha DATE,
                             presente BOOLEAN,
                             FOREIGN KEY (alumno_id) REFERENCES alumnos(id)
);

-- Insertar usuarios
INSERT INTO usuarios (id, nombre, contrasena, rol) VALUES
                                                       ('admin', 'Administrador', 'admin123', 'ADMIN'),
                                                       ('ana', 'Ana Ruiz', 'ana123', 'DOCENTE'),
                                                       ('juan', 'Juan Pérez', 'juan123', 'DOCENTE'),
                                                       ('carlos', 'Carlos Chávez', 'carlos123', 'DOCENTE'),
                                                       ('luisa', 'Luisa Martínez', 'luisa123', 'DOCENTE'),
                                                       ('miguel', 'Miguel Díaz', 'miguel123', 'DOCENTE');

-- Insertar alumnos
INSERT INTO alumnos (nombre) VALUES
                                 ('Carlos Sánchez'),
                                 ('Luisa Fernández'),
                                 ('Juan Pérez'),
                                 ('Ana Torres'),
                                 ('Miguel Díaz'),
                                 ('María Gómez'),
                                 ('José Martínez'),
                                 ('Laura Sánchez'),
                                 ('Pedro Ramírez'),
                                 ('Sofía López'),
                                 ('David Fernández'),
                                 ('Claudia Morales'),
                                 ('Andrés González'),
                                 ('Patricia Cruz'),
                                 ('Roberto Herrera');
ALTER TABLE asistencias ADD UNIQUE KEY (alumno_id, fecha);
