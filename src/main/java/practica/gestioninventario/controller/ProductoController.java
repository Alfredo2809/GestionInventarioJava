package practica.gestioninventario.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practica.gestioninventario.dto.ProductoRequest;
import practica.gestioninventario.dto.ProductoResponse;
import practica.gestioninventario.service.ProductoService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Gestion CRUD del inventario")
public class ProductoController {
    private final ProductoService servicio;

    public ProductoController(ProductoService servicio) { this.servicio = servicio; }

    @GetMapping
    @Operation(summary = "Consultar todos los productos")
    public List<ProductoResponse> listar() { return servicio.listar(); }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar producto por ID")
    public ProductoResponse obtener(@PathVariable Long id) { return servicio.obtener(id); }

    @PostMapping
    @Operation(summary = "Registrar un producto")
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest datos) {
        ProductoResponse creado = servicio.crear(datos);
        return ResponseEntity.created(URI.create("/api/productos/" + creado.id())).body(creado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto")
    public ProductoResponse actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequest datos) {
        return servicio.actualizar(id, datos);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
