package cl.moonson.carrito.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.moonson.carrito.model.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {

}
