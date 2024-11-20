package org.actividadut02.services;

import org.actividadut02.dataaccess.ProductDataAccess;
import org.actividadut02.entities.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService{

//    Implementacion la dependencia de ProductDataAccess
    private final ProductDataAccess productDataAccess;

//        Constructor
    public ProductServiceImpl(ProductDataAccess productDataAccess) {
        this.productDataAccess = productDataAccess;
    }

    /**
     * @return numero de productos
     */
    @Override
    public Long count() {
        return productDataAccess.count();
    }

    /**
     * @param id tipo String
     * @return true o false
     */
    @Override
    public boolean existsById(String id) {
        return productDataAccess.existsById(id);
    }

    /**
     * @param id tipo String
     * @return Optional
     * @throws SQLException
     */
    @Override
    public Optional<Product> findById(String id) throws SQLException {
        return productDataAccess.findById(id);
    }


    /**
     * @param product
     * @return new product
     */
    @Override
    public Product save(Product product) throws SQLException {
        if (product.getProductCode() == null || product.getProductCode().trim().isEmpty()) {
            throw new RuntimeException("El ID del producto no puede estar vacío\n");
        }
        if (product.getProductName() == null ) {
            throw new RuntimeException("El nombre del producto no puede estar vacío\n");
        }

        if (product.getBuyPrice() <= 0) {
            throw new RuntimeException("El precio de compra debe ser mayor que 0\n");
        }
           return productDataAccess.save(product);
    }

    /**
     * @param id tipo String
     */
    @Override
    public void deleteById(String id) {
        if (!productDataAccess.existsById(id)) {
            throw new RuntimeException("Producto no encontrado\n");
        }
        productDataAccess.deleteById(id);
    }

    /**
     * @return list of products
     */
    @Override
    public List<Product> findAll() {
        List<Product> products = productDataAccess.findAll();
        if (products.isEmpty()) {
            throw new RuntimeException("No existen productos");
        }
        return products;
    }

}
