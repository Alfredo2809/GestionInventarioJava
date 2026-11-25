package practica.gestioninventario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> noEncontrado(ProductoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("No encontrado", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> datosInvalidos(MethodArgumentNotValidException ex) {
        List<String> detalles = ex.getBindingResult().getFieldErrors().stream()
                .map(campo -> campo.getField() + ": " + campo.getDefaultMessage()).toList();
        Map<String, Object> cuerpo = error("Datos no validos", "Revisa los campos enviados");
        cuerpo.put("detalles", detalles);
        return ResponseEntity.badRequest().body(cuerpo);
    }

    private Map<String, Object> error(String titulo, String mensaje) {
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("error", titulo);
        cuerpo.put("mensaje", mensaje);
        cuerpo.put("fecha", OffsetDateTime.now().toString());
        return cuerpo;
    }
}
