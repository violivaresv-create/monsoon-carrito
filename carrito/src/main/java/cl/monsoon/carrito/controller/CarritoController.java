package cl.monsoon.carrito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.monsoon.carrito.model.Carrito;
import cl.monsoon.carrito.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/api/v0/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;


    @Operation(summary = "Crea un nuevo carrito de compras" , description = "Crea un nuevo carrito de compras vacío y devuelve su información")
    @PostMapping
    public ResponseEntity<EntityModel<Carrito>> crearCarrito() {
        boolean creado = carritoService.crearCarrito();
        if (creado) return ResponseEntity.status(HttpStatus.CREATED).body(addLinks(null));
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(summary = "Agrega un juego al carrito" , description = "Agrega un juego al carrito especificado por su ID y devuelve la información actualizada del carrito")
    @PostMapping("/{id}/agregar/{juegoId}")
    public ResponseEntity<EntityModel<Carrito>> agregarJuegoAlCarrito(@PathVariable Long id, @PathVariable Long juegoId) {
        Carrito carrito = carritoService.obtenerCarritoPorId(id);
        boolean agregado = carritoService.agregarJuegoAlCarrito(carrito, juegoId);
        if (agregado)return ResponseEntity.status(HttpStatus.OK).body(addLinks(carrito));
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @Operation(summary = "Obtiene un carrito por su ID" , description = "Obtiene un carrito de compras específico por su ID y devuelve su información")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Carrito>> obtenerCarrito(@PathVariable Long id) {
        Carrito carrito = carritoService.obtenerCarritoPorId(id);
        if(carrito != null) return ResponseEntity.status(HttpStatus.OK).body(addLinks(carrito));
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Elimina un juego del carrito" , description = "Elimina un juego específico del carrito de compras y devuelve la información actualizada del carrito")
    @DeleteMapping("/{id}/eliminarJuego/{juegoId}")
    public ResponseEntity<EntityModel<Carrito>> eliminarJuegoDelCarrito(@PathVariable Long id, @PathVariable Long juegoId) {
        Carrito carrito = carritoService.obtenerCarritoPorId(id);
        boolean eliminado = carritoService.eliminarJuegoDelCarrito(carrito, juegoId);
        if (eliminado) return ResponseEntity.status(HttpStatus.OK).body(addLinks(carrito));
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @Operation(summary = "Elimina un carrito" , description = "Elimina un carrito de compras específico por su ID y devuelve una confirmación")
    @DeleteMapping("/eliminarCarrito/{id}")
    public ResponseEntity<EntityModel<Carrito>> eliminarCarrito(@PathVariable Long id) {
        boolean eliminado = carritoService.eliminarCarrito(id);
        if (eliminado) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private EntityModel<Carrito> addLinks(Carrito carrito) {
        
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CarritoController.class).obtenerCarrito(carrito.getId())).withSelfRel();
        Link allLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CarritoController.class).agregarJuegoAlCarrito(carrito.getId(), null)).withRel("agregar-juego");
        return EntityModel.of(carrito, selfLink, allLink);
    }
    
}
