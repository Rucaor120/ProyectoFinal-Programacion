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
    dni VARCHAR(9) NOT NULL UNIQUE,
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
