package org.actividadut02.dataaccess;

import org.actividadut02.entities.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductDataAccess{

    Long count();
    boolean existsById(String id);
    Optional<Product> findById(String id) throws SQLException;
    List<Product> findAll();
    Product save(Product product) throws SQLException;
    void deleteById(String id);
}
