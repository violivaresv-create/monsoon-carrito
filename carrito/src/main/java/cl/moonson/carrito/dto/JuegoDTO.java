package cl.moonson.carrito.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class JuegoDTO {

    private Long id;
    private String titulo;
    private BigDecimal precio;

}
