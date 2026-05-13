CREATE DATABASE IF NOT EXISTS tiendapinturas;
USE tiendapinturas;

-- Tabla raíz de usuarios (OBLIGATORIA)
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dni VARCHAR(20) NOT NULL UNIQUE,
    rol ENUM('cliente', 'empleado') NOT NULL DEFAULT 'cliente'
);

-- Tabla hija 1 (Joined Table Inheritance)
CREATE TABLE IF NOT EXISTS clientes (
    usuario_id INT PRIMARY KEY,
    tipo_cliente ENUM('minorista', 'mayorista') NOT NULL DEFAULT 'minorista',
    CONSTRAINT fk_cli FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Tabla hija 2 (Joined Table Inheritance)
CREATE TABLE IF NOT EXISTS empleados (
    usuario_id INT PRIMARY KEY,
    turno ENUM('mañana', 'tarde', 'completo') NOT NULL DEFAULT 'completo',
    salario DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_emp FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Tabla entidad principal del dominio
CREATE TABLE IF NOT EXISTS pinturas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    color VARCHAR(50) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL DEFAULT 0
);

-- Tabla de relación N:M
CREATE TABLE IF NOT EXISTS compras (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    pintura_id INT NOT NULL,
    fecha DATE NOT NULL,
    cantidad INT NOT NULL,
    precio_total DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_compra_cli FOREIGN KEY (cliente_id) REFERENCES clientes(usuario_id) ON DELETE CASCADE,
    CONSTRAINT fk_compra_pin FOREIGN KEY (pintura_id) REFERENCES pinturas(id) ON DELETE CASCADE
);

-- ==========================================
-- DATOS DE PRUEBA (Opcional para testeo)
-- ==========================================

-- Insertar un empleado (Admin)
INSERT IGNORE INTO usuarios (id, username, password, email, nombre, apellidos, dni, rol) VALUES 
(1, 'admin', '1234', 'admin@tienda.com', 'Carlos', 'García', '11111111A', 'empleado');
INSERT IGNORE INTO empleados (usuario_id, turno, salario) VALUES 
(1, 'completo', 1500.00);

-- Insertar un cliente de prueba
INSERT IGNORE INTO usuarios (id, username, password, email, nombre, apellidos, dni, rol) VALUES 
(2, 'cliente1', '1234', 'cliente1@gmail.com', 'Ana', 'López', '22222222B', 'cliente');
INSERT IGNORE INTO clientes (usuario_id, tipo_cliente) VALUES 
(2, 'minorista');

-- Insertar pinturas de prueba en el inventario
INSERT IGNORE INTO pinturas (id, nombre, color, tipo, precio, stock) VALUES 
(1, 'Acrílica Premium', 'Blanco', 'Acrílica', 25.50, 100),
(2, 'Esmalte Sintético', 'Azul', 'Esmalte', 18.00, 50),
(3, 'Pintura Plástica Interior', 'Verde', 'Plástica', 30.00, 75);

