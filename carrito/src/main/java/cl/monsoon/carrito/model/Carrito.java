package cl.monsoon.carrito.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.boot.internal.CollectionClassification;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del carrito de compras", 
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @ElementCollection
    @CollectionTable(
        name = "carrito_juegos_ids",
        joinColumns = @JoinColumn(name = "carrito_id")
    )
    @Schema(description = "Lista de IDs de juegos en el carrito", 
            example = "[1, 2, 3]",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> juegosIds = new ArrayList<>();
    
    @Schema(description = "Total del carrito de compras", 
            example = "59.99",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal total;
}
