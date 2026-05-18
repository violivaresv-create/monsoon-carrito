package cl.monsoon.carrito.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.monsoon.carrito.model.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {

}
