package cl.monsoon.carrito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.monsoon.carrito.model.Carrito;
import cl.monsoon.carrito.service.CarritoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/api/v0/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @PostMapping
    public Carrito crearCarrito() {
        return carritoService.crearCarrito();
    }

    @PostMapping("/{id}/agregar/{juegoId}")
    public void agregarJuegoAlCarrito(@PathVariable Long id, @PathVariable Long juegoId) {
        Carrito carrito = carritoService.obtenerCarritoPorId(id);
        carritoService.agregarJuegoAlCarrito(carrito, juegoId);
    }

    @GetMapping("/{id}")
    public Carrito obtenerCarrito(@PathVariable Long id) {
        return carritoService.obtenerCarritoPorId(id);
    }

    
}
