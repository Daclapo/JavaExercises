package org.actividadut02.services;

import org.actividadut02.entities.Product;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/* Esta capa gestiona la lógica de negocio.
* Por ejemplo:
* - Utiliza la capa de acceso a datos para interactuar con la BD.
* - Puede realizar operaciones más complejas como validaciones o transformacion de datos*
* */
public interface ProductService {

    Long count();
    boolean existsById(String id);
    Optional<Product> findById(String id) throws SQLException;
    List<Product> findAll();
    Product save(Product product) throws SQLException;
    void deleteById(String id);

}
