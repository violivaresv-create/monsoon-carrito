package cl.moonson.carrito.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import cl.moonson.carrito.dto.JuegoDTO;
import cl.moonson.carrito.model.Carrito;
import cl.moonson.carrito.repository.CarritoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private WebClient webClient;

    public void agregarJuegoAlCarrito(Carrito carrito, Long juegoId) {
        JuegoDTO juego = webClient.get()
                    .uri("/api/v0/juegos/{id}", juegoId)
                    .retrieve()
                    .bodyToMono(JuegoDTO.class)
                    .block();
        carrito.getJuegos().add(juego);
        carritoRepository.save(carrito);
    }

    public Double calcularTotal(Carrito carrito) {
        Double total = 0.0;
        for (JuegoDTO juego : carrito.getJuegos()) {
            total += juego.getPrecio();
        }
        return total;
    }

    public Carrito obtenerCarritoPorId(Long id) {
        return carritoRepository.findById(id).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
    }
}
