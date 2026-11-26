package practica.gestioninventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practica.gestioninventario.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> { }
