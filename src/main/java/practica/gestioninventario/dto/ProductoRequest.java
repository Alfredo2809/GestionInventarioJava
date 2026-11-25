package practica.gestioninventario.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProductoRequest(
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 120, message = "Maximo 120 caracteres")
    String nombre,

    @Size(max = 500, message = "Maximo 500 caracteres")
    String descripcion,

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.00", message = "El precio no puede ser negativo")
    @Digits(integer = 10, fraction = 2, message = "El precio admite hasta 10 enteros y 2 decimales")
    BigDecimal precio,

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    Integer stock,

    @NotBlank(message = "La categoria es obligatoria")
    @Size(max = 80, message = "Maximo 80 caracteres")
    String categoria
) { }
