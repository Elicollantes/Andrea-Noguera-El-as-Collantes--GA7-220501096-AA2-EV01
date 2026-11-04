CREATE DATABASE IF NOT EXISTS sumapets
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

USE sumapets;

CREATE TABLE IF NOT EXISTS productos (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(60) NOT NULL,
    marca VARCHAR(100) NOT NULL,
    precio DECIMAL(12, 2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT chk_productos_precio_no_negativo CHECK (precio >= 0),
    CONSTRAINT chk_productos_stock_no_negativo CHECK (stock >= 0)
);

INSERT INTO productos (nombre, categoria, marca, precio, stock)
VALUES
    ('Alimento para perro adulto 2 kg', 'Alimento', 'NutriCan', 24900.00, 12),
    ('Arena sanitaria 4 kg', 'Higiene', 'MichiClean', 18900.00, 8),
    ('Correa mediana', 'Accesorios', 'PetWalk', 32000.00, 5);
 