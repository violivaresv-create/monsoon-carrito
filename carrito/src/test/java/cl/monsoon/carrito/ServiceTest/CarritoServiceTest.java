package cl.monsoon.carrito.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import cl.monsoon.carrito.controller.CarritoController;
import cl.monsoon.carrito.dto.JuegoDTO;
import cl.monsoon.carrito.model.Carrito;
import cl.monsoon.carrito.repository.CarritoRepository;
import cl.monsoon.carrito.service.CarritoService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarritoServiceTest {

    @Mock
    private CarritoRepository carritoRepository;

    @InjectMocks
    private CarritoService carritoService;

    private JuegoDTO dto;
    private Carrito carrito;
    
    @BeforeEach
    public void setUp() {
        
        dto = new JuegoDTO();
        dto.setId(1L);
        dto.setTitulo("Fortnite");
        dto.setPrecio(BigDecimal.valueOf(19.99));

        carrito = new Carrito();
        carrito.setId(1L);
        carrito.setJuegosIds(new ArrayList<>());
        carrito.setTotal(BigDecimal.ZERO);
    }

    @Test
    public void testCrearCarrito() {
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);

        boolean result = carritoService.crearCarrito();

        assertTrue(result);
        verify(carritoRepository, times(1)).save(any(Carrito.class));
    }

}