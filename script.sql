-- Script SQL para la base de datos del Sistema de Gestión de Contratos (SGC)

-- 1. Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS sgc_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2. Usar la base de datos recién creada o existente
USE sgc_db;

-- 3. Crear la tabla 'Usuario'
-- Contiene la información de los usuarios del sistema.
CREATE TABLE IF NOT EXISTS Usuario (
                                       username VARCHAR(50) PRIMARY KEY COMMENT 'Nombre de usuario, clave primaria',
    password VARCHAR(255) NOT NULL COMMENT 'Contraseña del usuario (debería ser hasheada en producción)',
    nombre VARCHAR(100) NOT NULL COMMENT 'Nombre completo del usuario',
    email VARCHAR(100) UNIQUE NOT NULL COMMENT 'Correo electrónico del usuario, debe ser único'
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla para almacenar información de los usuarios del sistema';

-- 4. Crear la tabla 'Contrato'
-- Contiene la información de los contratos gestionados.
CREATE TABLE IF NOT EXISTS Contrato (
                                        id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Identificador único del contrato, auto-incrementable',
                                        fechaFirma DATE NOT NULL COMMENT 'Fecha en que se firmó el contrato',
                                        fechaInicio DATE NOT NULL COMMENT 'Fecha de inicio de la vigencia del contrato',
                                        fechaFin DATE NOT NULL COMMENT 'Fecha de finalización de la vigencia del contrato',
                                        empresa VARCHAR(255) NOT NULL COMMENT 'Nombre de la empresa involucrada en el contrato',
    empleado VARCHAR(255) NOT NULL COMMENT 'Nombre del empleado asociado al contrato',
    funciones TEXT COMMENT 'Descripción de las funciones o servicios del contrato',
    monto DECIMAL(10, 2) NOT NULL COMMENT 'Monto económico total del contrato',
    frecuenciaDePago VARCHAR(50) NOT NULL COMMENT 'Frecuencia de pago (ej: Mensual, Trimestral, Anual)',
    usuario_username VARCHAR(50) COMMENT 'Clave foránea que referencia al usuario que gestiona este contrato',
    -- Definición de la clave foránea para relacionar con la tabla Usuario
    FOREIGN KEY (usuario_username) REFERENCES Usuario(username) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabla para almacenar información de los contratos';

-- 5. Insertar datos de ejemplo en la tabla 'Usuario'
INSERT INTO Usuario (username, password, nombre, email) VALUES
                                                            ('admin', 'admin123', 'Administrador Principal', 'admin@sgc.com'),
                                                            ('juanp', 'juanp123', 'Juan Pérez', 'juan.perez@sgc.com'),
                                                            ('mariag', 'mariag456', 'María García', 'maria.garcia@sgc.com');

-- 6. Insertar datos de ejemplo en la tabla 'Contrato'
INSERT INTO Contrato (fechaFirma, fechaInicio, fechaFin, empresa, empleado, funciones, monto, frecuenciaDePago, usuario_username) VALUES
                                                                                                                                      ('2023-01-15', '2023-02-01', '2024-01-31', 'Tech Solutions S.A.', 'Carlos Ruiz', 'Desarrollo de software a medida', 5000.00, 'Mensual', 'admin'),
                                                                                                                                      ('2023-03-01', '2023-03-10', '2024-03-09', 'Global Corp Ltda.', 'Ana López', 'Consultoría en ciberseguridad', 7500.00, 'Trimestral', 'admin'),
                                                                                                                                      ('2023-05-20', '2023-06-01', '2023-12-31', 'Innovatech S.A.S.', 'Pedro Gómez', 'Diseño y desarrollo de UX/UI', 4000.00, 'Mensual', 'juanp'),
                                                                                                                                      ('2024-02-10', '2024-03-01', '2025-02-28', 'Servicios Integrales Cia. Ltda.', 'Laura Fernández', 'Gestión de proyectos IT', 6000.00, 'Mensual', 'mariag');