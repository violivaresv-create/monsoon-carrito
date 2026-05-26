package cl.monsoon.carrito.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import cl.monsoon.carrito.dto.JuegoDTO;
import cl.monsoon.carrito.model.Carrito;
import cl.monsoon.carrito.repository.CarritoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private WebClient webClient;

    public Carrito crearCarrito() {
        Carrito carrito = new Carrito();
        carrito.setTotal(BigDecimal.ZERO);
    return carritoRepository.save(carrito);
    }

    public void agregarJuegoAlCarrito(Carrito carrito, Long juegoId){
        JuegoDTO juego = webClient.get()
                    .uri("/api/v0/juegos/{id}", juegoId)
                    .retrieve()
                    .bodyToMono(JuegoDTO.class)
                    .block();
        carrito.getJuegosIds().add(juegoId);
         carrito.setTotal(carrito.getTotal().add(juego.getPrecio()));
        carritoRepository.save(carrito);
    }

    public BigDecimal calcularTotal(Carrito carrito) {
        BigDecimal total = BigDecimal.ZERO;
        for (Long juegoId : carrito.getJuegosIds()) {
            JuegoDTO juego = webClient.get()
                    .uri("/api/v0/juegos/{id}", juegoId)
                    .retrieve()
                    .bodyToMono(JuegoDTO.class)
                    .block();
            total = total.add(juego.getPrecio());
        }
        return total;
    }

    public Carrito obtenerCarritoPorId(Long id) {
        return carritoRepository.findById(id).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
    }
}
