package cl.monsoon.carrito.ControllerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cl.monsoon.carrito.controller.CarritoController;
import cl.monsoon.carrito.model.Carrito;
import cl.monsoon.carrito.service.CarritoService;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarritoControllerTest {

    @Mock
    private CarritoService carritoService;

    @InjectMocks
    private CarritoController carritoController;

    private Carrito carrito;

    @BeforeEach
    public void setUp() {
        carrito = new Carrito();
        carrito.setId(1L);
        carrito.setJuegosIds(List.of(1L, 2L, 3L));
        carrito.setTotal(BigDecimal.valueOf(59.99));
    }

    @Test
    public void testObtenerCarrito() {
        when(carritoService.obtenerCarritoPorId(1L)).thenReturn(carrito);

        ResponseEntity<EntityModel<Carrito>> result = carritoController.obtenerCarrito(1L);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(carritoService, times(1)).obtenerCarritoPorId(1L);
    }

    @Test
    public void testObtenerCarritoNoEncontrado() {
        when(carritoService.obtenerCarritoPorId(1L)).thenReturn(null);

        ResponseEntity<EntityModel<Carrito>> result = carritoController.obtenerCarrito(1L);

        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(carritoService, times(1)).obtenerCarritoPorId(1L);
    }

    @Test
    public void testEliminarJuegoDelCarrito() {
        when(carritoService.obtenerCarritoPorId(1L)).thenReturn(carrito);
        when(carritoService.eliminarJuegoDelCarrito(carrito, 2L)).thenReturn(carrito);

        ResponseEntity<EntityModel<Carrito>> result = carritoController.eliminarJuegoDelCarrito(1L, 2L);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(carritoService, times(1)).obtenerCarritoPorId(1L);
        verify(carritoService, times(1)).eliminarJuegoDelCarrito(carrito, 2L);
    }
    @Test
    public void testEliminarJuegoDelCarritoNoEncontrado() {
        when(carritoService.obtenerCarritoPorId(1L)).thenReturn(carrito);
        when(carritoService.eliminarJuegoDelCarrito(carrito, 4L)).thenReturn(null);

        ResponseEntity<EntityModel<Carrito>> result = carritoController.eliminarJuegoDelCarrito(1L, 4L);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        verify(carritoService, times(1)).obtenerCarritoPorId(1L);
        verify(carritoService, times(1)).eliminarJuegoDelCarrito(carrito, 4L);
    }

    @Test
    public void testEliminarCarrito() {
        when(carritoService.eliminarCarrito(1L)).thenReturn(carrito);

        ResponseEntity<EntityModel<Carrito>> result = carritoController.eliminarCarrito(1L);

        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(carritoService, times(1)).eliminarCarrito(1L);
    }

    @Test
    public void testEliminarCarritoNoEncontrado() {
        when(carritoService.eliminarCarrito(1L)).thenReturn(null);

        ResponseEntity<EntityModel<Carrito>> result = carritoController.eliminarCarrito(1L);

        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(carritoService, times(1)).eliminarCarrito(1L);
    }

}
