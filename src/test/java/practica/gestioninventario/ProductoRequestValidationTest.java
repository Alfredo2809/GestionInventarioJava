package practica.gestioninventario;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import practica.gestioninventario.dto.ProductoRequest;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductoRequestValidationTest {
    @Test
    void datosCorrectosSonValidos() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validador = factory.getValidator();
            var dto = new ProductoRequest("Teclado", "USB", new BigDecimal("25.50"), 10, "Accesorios");
            assertTrue(validador.validate(dto).isEmpty());
        }
    }

    @Test
    void precioNegativoEsInvalido() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validador = factory.getValidator();
            var dto = new ProductoRequest("Teclado", "USB", new BigDecimal("-10.00"), 10, "Accesorios");
            assertFalse(validador.validate(dto).isEmpty());
        }
    }
}
