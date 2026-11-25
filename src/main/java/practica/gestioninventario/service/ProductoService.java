package practica.gestioninventario.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practica.gestioninventario.dto.ProductoRequest;
import practica.gestioninventario.dto.ProductoResponse;
import practica.gestioninventario.exception.ProductoNoEncontradoException;
import practica.gestioninventario.model.Producto;
import practica.gestioninventario.repository.ProductoRepository;

import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository repositorio;

    public ProductoService(ProductoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Transactional(readOnly = true)
    public List<ProductoResponse> listar() {
        return repositorio.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream().map(ProductoResponse::desde).toList();
    }

    @Transactional(readOnly = true)
    public ProductoResponse obtener(Long id) {
        return ProductoResponse.desde(obtenerEntidad(id));
    }

    @Transactional
    public ProductoResponse crear(ProductoRequest datos) {
        Producto producto = new Producto(limpiar(datos.nombre()), limpiarOpcional(datos.descripcion()),
                datos.precio(), datos.stock(), limpiar(datos.categoria()));
        return ProductoResponse.desde(repositorio.save(producto));
    }

    @Transactional
    public ProductoResponse actualizar(Long id, ProductoRequest datos) {
        Producto producto = obtenerEntidad(id);
        producto.actualizar(limpiar(datos.nombre()), limpiarOpcional(datos.descripcion()),
                datos.precio(), datos.stock(), limpiar(datos.categoria()));
        return ProductoResponse.desde(repositorio.save(producto));
    }

    @Transactional
    public void eliminar(Long id) {
        Producto producto = obtenerEntidad(id);
        repositorio.delete(producto);
    }

    private Producto obtenerEntidad(Long id) {
        return repositorio.findById(id).orElseThrow(() -> new ProductoNoEncontradoException(id));
    }

    private String limpiar(String valor) { return valor.trim(); }
    private String limpiarOpcional(String valor) { return valor == null ? null : valor.trim(); }
}
