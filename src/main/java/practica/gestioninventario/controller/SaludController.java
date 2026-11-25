package practica.gestioninventario.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class SaludController {
    @GetMapping("/health")
    public Map<String, String> salud() { return Map.of("estado", "OK", "aplicacion", "GestionInventario"); }
}
