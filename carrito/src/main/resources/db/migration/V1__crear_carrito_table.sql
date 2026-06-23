CREATE TABLE carrito (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total DECIMAL(10,2) NOT NULL
);

CREATE TABLE carrito_juegos_ids (
    carrito_id BIGINT NOT NULL,
    juegos_ids BIGINT NOT NULL,
    FOREIGN KEY (carrito_id) REFERENCES carrito(id)
);