package practica.gestioninventario.dto;

import practica.gestioninventario.model.Producto;
import java.math.BigDecimal;

public record ProductoResponse(
    Long id,
    String nombre,
    String descripcion,
    BigDecimal precio,
    Integer stock,
    String categoria
) {
    public static ProductoResponse desde(Producto p) {
        return new ProductoResponse(p.getId(), p.getNombre(), p.getDescripcion(),
                p.getPrecio(), p.getStock(), p.getCategoria());
    }
}
