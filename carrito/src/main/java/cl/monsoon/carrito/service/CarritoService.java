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
        carritoRepository.save(carrito);
        return carrito;
    }

    public Carrito agregarJuegoAlCarrito(Carrito carrito, Long juegoId){
        JuegoDTO juego = webClient.get()
                    .uri("/api/v0/juegos/juego/{id}", juegoId)
                    .retrieve()
                    .bodyToMono(JuegoDTO.class)
                    .block();
        carrito.getJuegosIds().add(juegoId);
        carrito.setTotal(carrito.getTotal().add(juego.getPrecio()));
        carritoRepository.save(carrito);
        return carrito;
    }

    public BigDecimal calcularTotal(Carrito carrito) {
        BigDecimal total = BigDecimal.ZERO;
        for (Long juegoId : carrito.getJuegosIds()) {
            JuegoDTO juego = webClient.get()
                    .uri("/api/v0/juegos/juego/{id}", juegoId)
                    .retrieve()
                    .bodyToMono(JuegoDTO.class)
                    .block();
            total = total.add(juego.getPrecio());
        }
        return total;
    }
    
    public Carrito eliminarJuegoDelCarrito(Carrito carrito, Long juegoId) {
        if (carrito.getJuegosIds().remove(juegoId)) {
            JuegoDTO juego = webClient.get()
                    .uri("/api/v0/juegos/juego/{id}", juegoId)
                    .retrieve()
                    .bodyToMono(JuegoDTO.class)
                    .block();
            carrito.setTotal(carrito.getTotal().subtract(juego.getPrecio()));
            carritoRepository.save(carrito);
            return carrito;
        }
        return null;
    }

    public Carrito eliminarCarrito(Long id) {
        Carrito carrito = obtenerCarritoPorId(id);
        carritoRepository.deleteById(id);
        return carrito;
    }


    public Carrito obtenerCarritoPorId(Long id) {
        return carritoRepository.findById(id).orElse(null);
    }
}
