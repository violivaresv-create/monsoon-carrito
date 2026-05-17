package cl.moonson.carrito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.moonson.carrito.model.Carrito;
import cl.moonson.carrito.service.CarritoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v0/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/{carritoId}/agregar")
    public void agregarJuegoalCarrito(@RequestParam Long Id, @RequestParam Long juegoId) {
        Carrito carrito = carritoService.obtenerCarritoPorId(Id);
        carritoService.agregarJuegoAlCarrito(carrito, juegoId);
    }
    

}
